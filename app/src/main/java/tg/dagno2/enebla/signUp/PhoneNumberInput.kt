package tg.dagno2.enebla.signUp

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import tg.dagno2.enebla.MainActivity
import tg.dagno2.enebla.R
import java.util.concurrent.TimeUnit

class PhoneNumberInput : AppCompatActivity() {
    var number: String = ""
    private lateinit var auth: FirebaseAuth // create instance of firebase auth
    // we will use this to match the sent otp from firebase
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
        setContentView(R.layout.activity_phone_number_input)

        auth=FirebaseAuth.getInstance()
        // Callback function for Phone Auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            // This method is called when the verification is completed
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
                Log.d("Msg", "onVerificationCompleted Success")
            }

            // Called when verification is failed add log statement to see the exception
            override fun onVerificationFailed(e: FirebaseException) {
                Log.d("MSg", "onVerificationFailed  $e")
            }

            // On code is sent by the firebase this method is called
            // in here we start a new activity where user can enter the OTP
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("Msg", "onCodeSent: $verificationId")
                storedVerificationId = verificationId
                resendToken = token

                // Start a new activity using intent
                // also send the storedVerificationId using intent
                // we will use this id to send the otp back to firebase
                val intent = Intent(applicationContext, OTP::class.java)
                intent.putExtra("data", arrayListOf<String>(storedVerificationId,number))
                startActivity(intent)
                finish()
            }
        }


        val phoneNumber = findViewById<EditText>(R.id.phone_number_input_in_pv)
        val agreed = findViewById<CheckBox>(R.id.term_agreement)
        val next = findViewById<Button>(R.id.su_pni_next_button)
        val terms = findViewById<TextView>(R.id.signup_terms)

        terms.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ethiotelecom.et/telebirr/")))
        }
        phoneNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (agreed.isChecked && phoneNumber.text.toString().length == 9){//TODO: USE REGEX
                    next.isEnabled = true
                    next.setTextColor(Color.parseColor("#E5E5E5"))
                }else {
                    next.isEnabled = false
                    next.setTextColor(Color.parseColor("#777474"))
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        agreed.setOnClickListener {
            if (agreed.isChecked && phoneNumber.text.toString().length == 9){//TODO: USE REGEX
                next.isEnabled = true
                next.setTextColor(Color.parseColor("#E5E5E5"))
            }else {
                next.isEnabled = false
                next.setTextColor(Color.parseColor("#777474"))
            }
        }
        next.setOnClickListener {
            number = "+251${phoneNumber.text.toString()}"
            //TODO Add thread here for the verification progress bar
            sendVerificationCode(number)
        }
    }
    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        Log.d("Msg", "Auth started")
    }
}