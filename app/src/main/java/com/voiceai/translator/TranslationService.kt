package com.voiceai.translator


class TranslationService {


    fun translate(
        text: String,
        from: String,
        to: String,
        callback: (String) -> Unit
    ) {


        val result =
            "Translation of: $text"


        callback(result)

    }

}
