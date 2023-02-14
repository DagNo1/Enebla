package tg.dagno2.enebla.signUp

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import tg.dagno2.enebla.MainActivity
import tg.dagno2.enebla.R

class PhoneNumberInput : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
        setContentView(R.layout.activity_phone_number_input)
        findViewById<TextView>(R.id.signup_terms).setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ethiotelecom.et/telebirr/")))
        }
        val phoneNumber = findViewById<EditText>(R.id.phone_number_input_in_pv)
        val agreed = findViewById<CheckBox>(R.id.term_agreement)
        val next = findViewById<Button>(R.id.su_pni_next_button)
        phoneNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (agreed.isChecked && phoneNumber.text.toString().length == 9){//TODO: USE REGEX
                    next.isEnabled = true
                    next.setTextColor(Color.parseColor("#FFFFFFFF"))
                }else {
                    next.isEnabled = false
                    next.setTextColor(Color.parseColor("#E5E5E5"))
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        agreed.setOnClickListener {
            if (agreed.isChecked && phoneNumber.text.toString().length == 9){//TODO: USE REGEX
                next.isEnabled = true
                next.setTextColor(Color.parseColor("#FFFFFFFF"))
            }else {
                next.isEnabled = false
                next.setTextColor(Color.parseColor("#E5E5E5"))
            }
        }
        next.setOnClickListener {
            startActivity(Intent(this,OTP::class.java))
        }
    }

}