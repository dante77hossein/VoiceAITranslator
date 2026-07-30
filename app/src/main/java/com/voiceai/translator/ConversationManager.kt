package com.voiceai.translator


class ConversationManager {


    var conversationMode =
        false



    var firstLanguage =
        "fa"



    var secondLanguage =
        "en"



    private var currentSpeaker =
        1




    fun switchSpeaker(){


        currentSpeaker =
            if(currentSpeaker == 1)
                2
            else
                1


    }




    fun getSourceLanguage():String{


        return if(currentSpeaker == 1)
            firstLanguage
        else
            secondLanguage


    }





    fun getTargetLanguage():String{


        return if(currentSpeaker == 1)
            secondLanguage
        else
            firstLanguage


    }


}
