package com.voiceai.translator

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)

        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setPadding(40,40,40,40)

        val title = TextView(this)

        title.text = "🎙 Voice AI Translator"
        title.textSize = 28f
        title.setTextColor(Color.BLACK)
        title.gravity = Gravity.CENTER


        val button = Button(this)

        button.text = "Start Translation"


        layout.addView(
            title,
            LinearLayout.LayoutParams(
                -1,
                150
            )
        )


        layout.addView(
            button,
            LinearLayout.LayoutParams(
                -1,
                120
            )
        )


        setContentView(layout)
    }
}
