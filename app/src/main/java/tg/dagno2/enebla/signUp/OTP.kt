package tg.dagno2.enebla.signUp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import com.noobcode.otpview.OTPView
import tg.dagno2.enebla.R

class OTP : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
        setContentView(R.layout.activity_otp)
        var otpView = findViewById<OTPView>(R.id.otp_input_signup)

        val verify = findViewById<Button>(R.id.su_otp_verify_button)
        
        otpView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (otpView.text.toString().length == 6){//TODO: USE REGEX
                    verify.isEnabled = true
                    verify.setTextColor(Color.parseColor("#E5E5E5"))
                }else {
                    verify.isEnabled = false
                    verify.setTextColor(Color.parseColor("#777474"))
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        verify.setOnClickListener {
            startActivity(Intent(this,PersonalInformationInput::class.java))
        }
    }
}