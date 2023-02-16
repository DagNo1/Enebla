package tg.dagno2.enebla.logIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import tg.dagno2.enebla.MainActivity
import tg.dagno2.enebla.R
import tg.dagno2.enebla.signUp.PhoneNumberInput

class LogIn : AppCompatActivity() {
    // create instance of firebase auth
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
        setContentView(R.layout.activity_log_in)
        auth = FirebaseAuth.getInstance()

        findViewById<TextView>(R.id.forgot_password_in_li).setOnClickListener {

        }
        findViewById<TextView>(R.id.register_button_in_li).setOnClickListener {
            startActivity(Intent(this, PhoneNumberInput::class.java))
        }
        findViewById<TextView>(R.id.li_button_in_li).setOnClickListener {
            auth.signOut()
            loginUser()
        }
    }
    private fun loginUser() {
        val email = findViewById<EditText>(R.id.e_input_in_li).text.toString().trim()
        val password = findViewById<EditText>(R.id.p_input_in_li).text.toString().trim()
        //TODO USE THREAD WHILE LOGGING IN
        if (email.isNotEmpty() && password.isNotEmpty()) {//TODO USE REGEX
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main) {
                        finishLoginUser()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LogIn, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun finishLoginUser() = startActivity(Intent(this, MainActivity::class.java))
}