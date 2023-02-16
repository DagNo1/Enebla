package tg.dagno2.enebla.logIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import tg.dagno2.enebla.MainActivity
import tg.dagno2.enebla.R
import tg.dagno2.enebla.databinding.ActivityLogInBinding
import tg.dagno2.enebla.signUp.PhoneNumberInput

class LogIn : AppCompatActivity() {
    // create instance of firebase auth
    lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.forgotPasswordInLi.setOnClickListener {

        }
        binding.registerButtonInLi.setOnClickListener {
            startActivity(Intent(this, PhoneNumberInput::class.java))
        }
        binding.liButtonInLi.setOnClickListener {
            showProgressBar()
            auth.signOut()
            loginUser()
        }
    }
    private fun loginUser() {
        val email = binding.eInputInLi.text.toString().trim()
        val password = binding.pInputInLi.text.toString().trim()
        if (email.isNotEmpty() && password.isNotEmpty()) {//TODO USE REGEX
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main) {
                        finishLoginUser()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        hideProgressBar()
                        Toast.makeText(this@LogIn, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else {
            Toast.makeText(this@LogIn, "* fields are required", Toast.LENGTH_LONG).show()
            hideProgressBar()
        }
    }
    private fun finishLoginUser()  {
        val intent =Intent(this,MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
    private fun hideProgressBar(){
        binding.progressBar.isVisible = false
        binding.liButtonInLi.isVisible = true
    }
    private fun showProgressBar(){
        binding.progressBar.isVisible = true
        binding.liButtonInLi.isVisible = false
    }
}