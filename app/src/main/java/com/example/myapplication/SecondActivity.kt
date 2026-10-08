package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val message = intent.getStringExtra(EXTRA_MESSAGE)
            ?.takeIf(String::isNotBlank)
            ?: getString(R.string.message_missing_error)
        findViewById<TextView>(R.id.messageText).text = message
    }

    companion object {
        const val EXTRA_MESSAGE = "com.example.myapplication.EXTRA_MESSAGE"
    }
}
