package com.voiceai.translator


class OpenAIClient {


    private val apiUrl =
        "https://api.openai.com/v1/chat/completions"



    fun createPrompt(
        text: String,
        targetLanguage: String
    ): String {


        return """
        Translate this text to $targetLanguage:

        $text
        """.trimIndent()

    }

}
