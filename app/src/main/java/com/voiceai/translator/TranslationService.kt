package com.voiceai.translator

import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONObject


class TranslationService {


    fun translate(
        text: String,
        source: String,
        target: String,
        callback: (String) -> Unit
    ) {


        Thread {


            try {


                val url =
                    URL("https://libretranslate.com/translate")


                val connection =
                    url.openConnection()
                            as HttpURLConnection


                connection.requestMethod =
                    "POST"


                connection.setRequestProperty(
                    "Content-Type",
                    "application/json"
                )


                connection.doOutput = true



                val json =
                    JSONObject()


                json.put(
                    "q",
                    text
                )


                json.put(
                    "source",
                    source
                )


                json.put(
                    "target",
                    target
                )


                json.put(
                    "format",
                    "text"
                )



                connection.outputStream.use {

                    it.write(
                        json.toString()
                            .toByteArray()
                    )

                }



                val response =
                    connection.inputStream
                        .bufferedReader()
                        .readText()



                val result =
                    JSONObject(response)
                        .getString(
                            "translatedText"
                        )



                runOnMain(callback, result)



            } catch(e: Exception){


                runOnMain(
                    callback,
                    "Translation error"
                )


            }


        }.start()

    }



    private fun runOnMain(
        callback:(String)->Unit,
        result:String
    ){

        callback(result)

    }


}
