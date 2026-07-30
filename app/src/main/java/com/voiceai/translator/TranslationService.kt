package com.voiceai.translator

import android.os.Handler
import android.os.Looper
import java.net.URL
import java.net.HttpURLConnection
import java.net.URLEncoder
import org.json.JSONObject


class TranslationService {


    private val handler = Handler(Looper.getMainLooper())


    fun translate(
        text: String,
        source: String,
        target: String,
        callback: (String)->Unit
    ) {


        Thread {


            try {


                val encodedText =
                    URLEncoder.encode(
                        text,
                        "UTF-8"
                    )


                val url =
                    URL(
                        "https://api.mymemory.translated.net/get?q=$encodedText&langpair=$source|$target"
                    )


                val connection =
                    url.openConnection()
                            as HttpURLConnection


                connection.requestMethod = "GET"

                connection.connectTimeout = 10000

                connection.readTimeout = 10000



                val response =
                    connection.inputStream
                        .bufferedReader()
                        .readText()



                val json =
                    JSONObject(response)


                val translated =
                    json
                        .getJSONObject("responseData")
                        .getString("translatedText")



                handler.post {

                    callback(translated)

                }



            } catch(e:Exception){


                handler.post {

                    callback(
                        "Translation error: ${e.message}"
                    )

                }

            }


        }.start()

    }


}
