package com.voiceai.translator

import android.os.Handler
import android.os.Looper
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL


class TranslationService {


    private val mainHandler =
        Handler(Looper.getMainLooper())


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


                connection.requestMethod = "POST"

                connection.setRequestProperty(
                    "Content-Type",
                    "application/json"
                )


                connection.connectTimeout = 10000

                connection.readTimeout = 10000

                connection.doOutput = true



                val json =
                    JSONObject()

                json.put("q", text)

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



                val translated =
                    JSONObject(response)
                        .optString(
                            "translatedText",
                            "No translation"
                        )


                mainHandler.post {

                    callback(translated)

                }



            } catch (e: Exception) {


                mainHandler.post {

                    callback(
                        "Translation failed: ${e.message}"
                    )

                }


            }


        }.start()


    }

}
