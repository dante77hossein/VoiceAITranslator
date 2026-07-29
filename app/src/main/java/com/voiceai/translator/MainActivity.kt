package com.voiceai.translator

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.widget.*
import android.graphics.Color
import android.view.Gravity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : Activity() {


    private var recorder: MediaRecorder? = null


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

                Toast.makeText(
                    this,
                    "🎤 Listening...",
                    Toast.LENGTH_SHORT
                ).show()

            }

        }


        layout.addView(title)

        layout.addView(button)


        setContentView(layout)

    }

}
