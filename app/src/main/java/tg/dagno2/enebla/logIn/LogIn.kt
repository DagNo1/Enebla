package tg.dagno2.enebla.logIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import tg.dagno2.enebla.R
import tg.dagno2.enebla.signUp.PhoneNumberInput

class LogIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
        setContentView(R.layout.activity_log_in)

        findViewById<TextView>(R.id.forgot_password_in_li).setOnClickListener {

        }
        findViewById<TextView>(R.id.register_button_in_li).setOnClickListener {
            startActivity(Intent(this, PhoneNumberInput::class.java))
        }
    }
}