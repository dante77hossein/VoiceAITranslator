package com.voiceai.translator


class VoiceActivityDetector {


    private var lastVoiceTime =
        System.currentTimeMillis()



    fun detect(
        audioLevel:Double
    ):Boolean{


        val threshold = 0.02



        if(audioLevel > threshold){

            lastVoiceTime =
                System.currentTimeMillis()

            return true

        }



        return (
            System.currentTimeMillis()
            -
            lastVoiceTime
        ) < 1200


    }


}
