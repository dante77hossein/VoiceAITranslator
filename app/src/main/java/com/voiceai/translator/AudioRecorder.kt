package com.voiceai.translator

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder


class AudioRecorder {


    private var recorder: AudioRecord? = null


    private var isRecording = false



    private val sampleRate = 16000



    fun start(
        callback:(ShortArray)->Unit
    ){


        val bufferSize =
            AudioRecord.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )



        recorder =
            AudioRecord(
                MediaRecorder.AudioSource.MIC,
                sampleRate,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize
            )



        recorder?.startRecording()


        isRecording = true



        Thread {


            val buffer =
                ShortArray(bufferSize)



            while(isRecording){


                val read =
                    recorder?.read(
                        buffer,
                        0,
                        buffer.size
                    )
                    ?:0



                if(read > 0){


                    callback(
                        buffer.copyOf(read)
                    )


                }


            }



        }.start()


    }




    fun stop(){


        isRecording = false


        recorder?.stop()

        recorder?.release()

        recorder = null


    }


}
