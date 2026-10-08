package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.util.Patterns
import android.content.ActivityNotFoundException
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val messageInput = findViewById<EditText>(R.id.messageInput)
        val openButton = findViewById<Button>(R.id.openSecondActivityButton)
        val phoneInput = findViewById<EditText>(R.id.phoneInput)
        val callButton = findViewById<Button>(R.id.callFriendButton)
        val shareButton = findViewById<Button>(R.id.shareTextButton)

        openButton.setOnClickListener {
            val message = messageInput.text.toString().trim()
            if (message.isEmpty()) {
                messageInput.error = getString(R.string.message_validation_error)
                messageInput.requestFocus()
            } else {
                val intent = Intent(this, SecondActivity::class.java)
                    .putExtra(SecondActivity.EXTRA_MESSAGE, message)
                startActivity(intent)
            }
        }

        callButton.setOnClickListener {
            val phoneNumber = phoneInput.text.toString().trim()
            val digitCount = phoneNumber.count(Char::isDigit)
            if (!Patterns.PHONE.matcher(phoneNumber).matches() || digitCount !in 3..15) {
                phoneInput.error = getString(R.string.phone_validation_error)
                phoneInput.requestFocus()
            } else {
                val dialIntent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null))
                try {
                    startActivity(dialIntent)
                } catch (_: ActivityNotFoundException) {
                    phoneInput.error = getString(R.string.dialer_unavailable_error)
                }
            }
        }

        shareButton.setOnClickListener {
            val text = messageInput.text.toString().trim()
            if (text.isEmpty()) {
                messageInput.error = getString(R.string.message_validation_error)
                messageInput.requestFocus()
            } else {
                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, text)
                }
                startActivity(Intent.createChooser(sendIntent, getString(R.string.share_chooser_title)))
            }
        }
    }
}
