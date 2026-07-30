package com.voiceai.translator


import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.content.pm.PackageManager
import android.widget.*
import android.view.Gravity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat



class MainActivity : Activity() {


    private lateinit var originalText: TextView
    private lateinit var translatedText: TextView


    private val audioRecorder =
        AudioRecorder()


    private val voiceFilter =
        VoiceFilterEngine()



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)



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





        originalText =
            TextView(this)


        originalText.text =
            "Waiting for voice..."


        originalText.textSize =
            20f





        translatedText =
            TextView(this)


        translatedText.text =
            "Translation..."


        translatedText.textSize =
            20f





        val button =
            Button(this)



        button.text =
            "🎤 START LISTENING"





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

        layout.addView(originalText)

        layout.addView(translatedText)

        layout.addView(button)



        setContentView(layout)


    }






    private fun startVoiceEngine(){



        originalText.text =
            "🎤 Listening..."



        audioRecorder.start { audio ->



            voiceFilter.process(
                audio
            ){ hasVoice, cleanAudio ->



                runOnUiThread {



                    if(hasVoice){


                        originalText.text =
                            "🎤 Human voice detected"



                    }else{


                        originalText.text =
                            "🔇 Noise ignored"



                    }


                }


            }


        }


    }






    override fun onDestroy(){

        super.onDestroy()


        audioRecorder.stop()


    }



}
