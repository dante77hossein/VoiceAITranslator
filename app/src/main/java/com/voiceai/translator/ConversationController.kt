package com.voiceai.translator


class ConversationController {


    var enabled =
        false



    var isSpeaking =
        false




    fun start(){

        enabled = true

    }




    fun stop(){

        enabled = false

    }




    fun canListen():Boolean{


        return enabled && !isSpeaking


    }



}
