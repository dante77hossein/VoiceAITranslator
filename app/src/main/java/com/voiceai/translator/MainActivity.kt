package com.voiceai.translator


import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import android.widget.*
import android.view.Gravity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : Activity() {


    private lateinit var originalText: TextView
    private lateinit var translatedText: TextView

    private val SPEECH_REQUEST = 200


    private val translationService =
        TranslationService()



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


                startSpeech()

            }


        }



        layout.addView(title)

        layout.addView(originalText)

        layout.addView(translatedText)

        layout.addView(button)



        setContentView(layout)

    }





    private fun startSpeech(){


        val intent =
            Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH
            )



        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )



        // تشخیص فارسی
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            "fa-IR"
        )



        // چند نتیجه برای دقت بیشتر
        intent.putExtra(
            RecognizerIntent.EXTRA_MAX_RESULTS,
            3
        )



        // پایان جمله بعد از سکوت کوتاه
        intent.putExtra(
            RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS,
            1500
        )



        intent.putExtra(
            RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS,
            1000
        )



        intent.putExtra(
            RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS,
            500
        )



        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            "صحبت کنید..."
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


            val results =
                data?.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                )



            val text =
                results?.firstOrNull()
                    ?: ""



            originalText.text =
                "متن اصلی:\n$text"




            if(text.isNotEmpty()){


                translationService.translate(
                    text,
                    "fa",
                    "en"
                ){ result ->



                    runOnUiThread {


                        translatedText.text =
                            "ترجمه:\n$result"


                    }


                }


            }



        }


    }


}
