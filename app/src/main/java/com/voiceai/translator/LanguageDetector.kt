package com.voiceai.translator


class LanguageDetector {


    fun detect(text:String):String {


        return when {


            text.matches(
                Regex(".*[آ-ی].*")
            ) -> {

                "fa"

            }


            text.matches(
                Regex(".*[一-龯].*")
            ) -> {

                "zh"

            }


            text.matches(
                Regex(".*[äöüßÄÖÜ].*")
            ) -> {

                "de"

            }


            else -> {


                "en"

            }


        }


    }


}
