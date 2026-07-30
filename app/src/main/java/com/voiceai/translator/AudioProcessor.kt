package com.voiceai.translator

import android.media.audiofx.AcousticEchoCanceler
import android.media.audiofx.AutomaticGainControl
import android.media.audiofx.NoiseSuppressor


class AudioProcessor {


    fun enableNoiseControl(
        audioSessionId:Int
    ){


        if(
            NoiseSuppressor.isAvailable()
        ){

            val ns =
                NoiseSuppressor.create(
                    audioSessionId
                )

            ns?.enabled = true

        }



        if(
            AcousticEchoCanceler.isAvailable()
        ){

            val echo =
                AcousticEchoCanceler.create(
                    audioSessionId
                )

            echo?.enabled = true

        }



        if(
            AutomaticGainControl.isAvailable()
        ){

            val agc =
                AutomaticGainControl.create(
                    audioSessionId
                )

            agc?.enabled = true

        }


    }


}
