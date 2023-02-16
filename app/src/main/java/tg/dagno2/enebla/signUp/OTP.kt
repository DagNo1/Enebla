package tg.dagno2.enebla.signUp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.*
import com.noobcode.otpview.OTPView
import tg.dagno2.enebla.R

class OTP : AppCompatActivity() {
    // get reference of the firebase auth
    lateinit var auth: FirebaseAuth
    lateinit var number :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
        setContentView(R.layout.activity_otp)
        auth=FirebaseAuth.getInstance()
        val storedVerificationId = intent.getStringArrayListExtra("data")?.get(0) // get storedVerificationId from the intent
        number = intent.getStringArrayListExtra("data")?.get(1).toString()
        val otpView = findViewById<OTPView>(R.id.otp_input_signup)
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
            val otp = otpView.text.toString()
            val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(storedVerificationId.toString(), otp)
            signInWithPhoneAuthCredential(credential)//TODO USE THREADS
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,"Valid OTP", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this , PersonalInformationInput::class.java)
                    intent.putExtra("phoneNumber",number)
                    startActivity(intent)
                    //TODO ones verified delete the user with the phone
                    finish()
                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(this,"Invalid OTP", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

}