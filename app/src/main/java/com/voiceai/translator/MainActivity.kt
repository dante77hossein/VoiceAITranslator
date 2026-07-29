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


    private val languages = arrayOf(
        "فارسی",
        "English",
        "Deutsch",
        "中文",
        "العربية",
        "Türkçe"
    )


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        val layout = LinearLayout(this)

        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setPadding(40,40,40,40)



        val title = TextView(this)

        title.text = "🎙 Voice AI Translator"

        title.textSize = 26f

        title.gravity = Gravity.CENTER



        val inputLabel = TextView(this)

        inputLabel.text = "زبان صحبت کردن"



        val inputSpinner = Spinner(this)

        inputSpinner.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                languages
            )



        val outputLabel = TextView(this)

        outputLabel.text = "ترجمه به"



        val outputSpinner = Spinner(this)

        outputSpinner.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                languages
            )



        originalText = TextView(this)

        originalText.text =
            "متن اصلی..."

        originalText.textSize = 18f



        translatedText = TextView(this)

        translatedText.text =
            "ترجمه..."

        translatedText.textSize = 18f



        val button = Button(this)

        button.text =
            "🎤 START TRANSLATION"



        button.setOnClickListener {


            if(ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.RECORD_AUDIO
                )
                != PackageManager.PERMISSION_GRANTED){


                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.RECORD_AUDIO
                    ),
                    100
                )


            }else{

                startSpeech(
                    inputSpinner.selectedItem.toString()
                )

            }

        }



        layout.addView(title)

        layout.addView(inputLabel)

        layout.addView(inputSpinner)

        layout.addView(outputLabel)

        layout.addView(outputSpinner)

        layout.addView(originalText)

        layout.addView(translatedText)

        layout.addView(button)



        setContentView(layout)

    }



    private fun startSpeech(language:String){


        val intent =
            Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH
            )


        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )


        val locale =
            when(language){

                "فارسی" ->
                    "fa-IR"

                "English" ->
                    "en-US"

                "Deutsch" ->
                    "de-DE"

                "中文" ->
                    "zh-CN"

                "العربية" ->
                    "ar-SA"

                "Türkçe" ->
                    "tr-TR"

                else ->
                    "en-US"
            }



        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            locale
        )


        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            "Speak..."
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


            val result =
                data?.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                )


            originalText.text =
                result?.get(0)
                    ?: "No result"


            translatedText.text =
                "ترجمه در مرحله بعد اضافه می‌شود"

        }

    }

}
