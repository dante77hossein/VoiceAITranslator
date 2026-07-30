package com.voiceai.translator


import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.widget.*
import android.view.Gravity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.Locale



class MainActivity : Activity() {


    private lateinit var originalText: TextView
    private lateinit var translatedText: TextView

    private lateinit var inputSpinner: Spinner
    private lateinit var outputSpinner: Spinner


    private lateinit var textToSpeech: TextToSpeech


    private val SPEECH_REQUEST = 200



    private val audioRecorder =
        AudioRecorder()


    private val voiceFilter =
        VoiceFilterEngine()



    private val translationService =
        TranslationService()



    private val conversationManager =
        ConversationManager()


    private var conversationMode = false



    private val languages =
        arrayOf(
            "فارسی",
            "English",
            "Deutsch",
            "中文",
            "Türkçe",
            "العربية"
        )



    private val languageCodes =
        mapOf(

            "فارسی" to "fa",
            "English" to "en",
            "Deutsch" to "de",
            "中文" to "zh",
            "Türkçe" to "tr",
            "العربية" to "ar"

        )




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)



        textToSpeech =
            TextToSpeech(this){ status ->

                if(status == TextToSpeech.SUCCESS){

                    textToSpeech.language =
                        Locale.US

                }

            }



        val layout =
            LinearLayout(this)


        layout.orientation =
            LinearLayout.VERTICAL


        layout.gravity =
            Gravity.CENTER


        layout.setPadding(
            40,
            40,
            40,
            40
        )



        val title =
            TextView(this)


        title.text =
            "🎙 Voice AI Translator"


        title.textSize =
            26f




        val inputLabel =
            TextView(this)

        inputLabel.text =
            "زبان صحبت کردن"




        inputSpinner =
            Spinner(this)


        inputSpinner.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                languages
            )




        val outputLabel =
            TextView(this)


        outputLabel.text =
            "ترجمه به"




        outputSpinner =
            Spinner(this)


        outputSpinner.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                languages
            )




        originalText =
            TextView(this)


        originalText.text =
            "متن اصلی..."

        originalText.textSize =
            20f




        translatedText =
            TextView(this)


        translatedText.text =
            "ترجمه..."

        translatedText.textSize =
            20f




        val conversationButton =
            Button(this)


        conversationButton.text =
            "🔄 Conversation Mode OFF"



        conversationButton.setOnClickListener {


            conversationMode =
                !conversationMode


            conversationManager.conversationMode =
                conversationMode



            if(conversationMode){

                conversationButton.text =
                    "🔄 Conversation Mode ON"


                Toast.makeText(
                    this,
                    "Conversation Mode Enabled",
                    Toast.LENGTH_SHORT
                ).show()


            }else{


                conversationButton.text =
                    "🔄 Conversation Mode OFF"


            }


        }





        val button =
            Button(this)


        button.text =
            "🎤 START TRANSLATION"




        button.setOnClickListener {


            if(
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.RECORD_AUDIO
                )
                != PackageManager.PERMISSION_GRANTED
            ){


                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.RECORD_AUDIO
                    ),
                    100
                )


            }else{


                startVoiceEngine()


            }


        }






        layout.addView(title)

        layout.addView(inputLabel)

        layout.addView(inputSpinner)

        layout.addView(outputLabel)

        layout.addView(outputSpinner)

        layout.addView(originalText)

        layout.addView(translatedText)

        layout.addView(conversationButton)

        layout.addView(button)



        setContentView(layout)


    }






    private fun startVoiceEngine(){


        originalText.text =
            "🎤 Listening..."



        audioRecorder.start { audio ->



            voiceFilter.process(
                audio
            ){ hasVoice, _ ->



                if(hasVoice){


                    audioRecorder.stop()


                    startSpeechRecognition()


                }


            }


        }


    }







    private fun startSpeechRecognition(){


        val intent =
            Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH
            )



        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )



        val selected =
            inputSpinner.selectedItem.toString()



        val locale =
            when(selected){

                "فارسی" -> "fa-IR"

                "English" -> "en-US"

                "Deutsch" -> "de-DE"

                "中文" -> "zh-CN"

                "Türkçe" -> "tr-TR"

                "العربية" -> "ar-SA"

                else -> "fa-IR"

            }



        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            locale
        )



        intent.putExtra(
            RecognizerIntent.EXTRA_MAX_RESULTS,
            3
        )



        startActivityForResult(
            intent,
            SPEECH_REQUEST
        )


    }








    override fun onActivityResult(
        requestCode:Int,
        resultCode:Int,
        data:Intent?
    ){

        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )



        if(
            requestCode == SPEECH_REQUEST &&
            resultCode == RESULT_OK
        ){



            val text =
                data?.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                )
                ?.get(0)
                ?: ""



            originalText.text =
                "متن اصلی:\n$text"





            val source:String

            val target:String



            if(conversationMode){


                source =
                    conversationManager.getSourceLanguage()


                target =
                    conversationManager.getTargetLanguage()


            }else{


                source =
                    languageCodes[
                        inputSpinner.selectedItem.toString()
                    ]
                    ?: "fa"



                target =
                    languageCodes[
                        outputSpinner.selectedItem.toString()
                    ]
                    ?: "en"

            }





            translationService.translate(
                text,
                source,
                target
            ){ result ->



                runOnUiThread {


                    translatedText.text =
                        "ترجمه:\n$result"



                    speakTranslation(
                        result,
                        target
                    )



                    if(conversationMode){

                        conversationManager.switchSpeaker()

                    }



                }


            }


        }


    }








    private fun speakTranslation(
        text:String,
        language:String
    ){


        val locale =
            when(language){


                "fa" -> Locale("fa","IR")

                "de" -> Locale.GERMAN

                "zh" -> Locale.CHINESE

                "tr" -> Locale("tr","TR")

                "ar" -> Locale("ar","SA")

                else -> Locale.US

            }




        textToSpeech.language =
            locale




        textToSpeech.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "translation"
        )


    }






    override fun onDestroy(){

        super.onDestroy()


        audioRecorder.stop()


        textToSpeech.stop()

        textToSpeech.shutdown()


    }


}
