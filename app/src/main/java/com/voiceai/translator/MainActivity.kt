package com.voiceai.translator

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.*
import android.widget.*
import android.view.Gravity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : Activity() {


    private lateinit var originalText: TextView
    private lateinit var translatedText: TextView

    private val translationService =
        TranslationService()


    private var speechRecognizer: SpeechRecognizer? = null



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        val layout = LinearLayout(this)

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


        val title = TextView(this)

        title.text =
            "🎙 Voice AI Translator"

        title.textSize =
            26f



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

                startListening()

            }


        }



        layout.addView(title)
        layout.addView(originalText)
        layout.addView(translatedText)
        layout.addView(button)


        setContentView(layout)

    }




    private fun startListening(){


        speechRecognizer =
            SpeechRecognizer.createSpeechRecognizer(this)



        speechRecognizer?.setRecognitionListener(
            object : RecognitionListener {


                override fun onResults(bundle: Bundle?) {


                    val results =
                        bundle?.getStringArrayList(
                            SpeechRecognizer.RESULTS_RECOGNITION
                        )


                    val text =
                        results?.get(0) ?: ""



                    showTranslation(text)


                }



                override fun onPartialResults(bundle: Bundle?) {


                    val results =
                        bundle?.getStringArrayList(
                            SpeechRecognizer.RESULTS_RECOGNITION
                        )


                    val text =
                        results?.get(0) ?: ""


                    originalText.text =
                        "متن اصلی:\n$text"


                }



                override fun onError(error:Int){

                    originalText.text =
                        "Speech error: $error"

                }



                override fun onReadyForSpeech(params:Bundle?){}
                override fun onBeginningOfSpeech(){}
                override fun onRmsChanged(rms:Float){}
                override fun onBufferReceived(buffer:ByteArray?){}
                override fun onEndOfSpeech(){}
                override fun onEvent(event:Int,params:Bundle?){}

            }
        )



        val intent =
            Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH
            )


        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )


        intent.putExtra(
            RecognizerIntent.EXTRA_PARTIAL_RESULTS,
            true
        )


        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            "fa-IR"
        )


        speechRecognizer?.startListening(intent)


    }




    private fun showTranslation(text:String){


        originalText.text =
            "متن اصلی:\n$text"



        translationService.translate(
            text,
            "fa",
            "en"
        ){ result ->


            translatedText.text =
                "ترجمه:\n$result"


        }

    }



    override fun onDestroy(){

        super.onDestroy()

        speechRecognizer?.destroy()

    }


}
