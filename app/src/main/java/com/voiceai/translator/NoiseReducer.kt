package com.voiceai.translator


class NoiseReducer {


    fun process(
        input:ShortArray
    ):ShortArray{


        val output =
            ShortArray(input.size)



        for(i in input.indices){


            output[i] =
                if(
                    kotlin.math.abs(
                        input[i].toInt()
                    ) < 500
                ){

                    0

                }else{

                    input[i]

                }


        }


        return output

    }


}
