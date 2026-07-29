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
import java.util.Locale


class MainActivity : Activity() {


    private lateinit var resultText: TextView

    private val SPEECH_REQUEST = 200


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val layout = LinearLayout(this)

        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setPadding(40,40,40,40)


        val title = TextView(this)

        title.text = "🎙 Voice AI Translator"
        title.textSize = 28f
        title.gravity = Gravity.CENTER


        resultText = TextView(this)

        resultText.text = "Speak something..."
        resultText.textSize = 20f
        resultText.gravity = Gravity.CENTER


        val button = Button(this)

        button.text = "START TRANSLATION"


        button.setOnClickListener {


            if(ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.RECORD_AUDIO
                ) != PackageManager.PERMISSION_GRANTED){


                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    100
                )


            }else{

                startSpeechRecognition()

            }

        }


        layout.addView(title)
        layout.addView(resultText)
        layout.addView(button)


        setContentView(layout)

    }



    private fun startSpeechRecognition(){

        val intent = Intent(
            RecognizerIntent.ACTION_RECOGNIZE_SPEECH
        )

        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )


        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            Locale.getDefault()
        )


        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            "Speak now..."
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


        if(requestCode == SPEECH_REQUEST &&
            resultCode == RESULT_OK){


            val result =
                data?.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                )


            resultText.text =
                result?.get(0) ?: ""

        }

    }

}
