package com.voiceai.translator


class VoiceFilterEngine {


    private val noiseReducer =
        NoiseReducer()


    private val vad =
        VoiceActivityDetector()



    fun process(
        audio: ShortArray,
        callback:(Boolean, ShortArray)->Unit
    ){


        // حذف نویز اولیه
        val cleanAudio =
            noiseReducer.process(audio)



        // محاسبه سطح صدا
        var sum = 0.0


        for(sample in cleanAudio){

            sum +=
                kotlin.math.abs(
                    sample.toDouble()
                )

        }



        val level =
            sum / cleanAudio.size / 32768.0



        // بررسی وجود صدای واقعی
        val hasVoice =
            vad.detect(level)



        callback(
            hasVoice,
            cleanAudio
        )


    }


}
