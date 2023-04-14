package tg.dagno2.enebla.login_signup

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import kotlinx.coroutines.*
//import com.google.firebase.auth.FirebaseAuth
import tg.dagno2.enebla.HomePage
import tg.dagno2.enebla.databinding.ActivityLogInBinding
import tg.dagno2.enebla.firstTimer.Boarding

class LogIn : AppCompatActivity() {

//    lateinit var auth: FirebaseAuth // create instance of firebase auth
    private lateinit var binding: ActivityLogInBinding
    private var isLoading = true
    private suspend fun junction() {
        val sharedPreferences = getSharedPreferences("LoadUp", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("first_time",true)|| true){
            val onBoarding = Intent(this, Boarding::class.java)
            onBoarding.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(onBoarding)
        }
//        if (auth.currentUser != null) {
//            val homePage = Intent(this, HomePage::class.java)
//            login.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(homePage)
//        }
        delay(500L)
        isLoading = false

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                isLoading
            }
        }
        runBlocking {
            junction()
        }
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        auth = FirebaseAuth.getInstance()
        binding.forgotPasswordInLi.setOnClickListener {

        }
        binding.registerButtonInLi.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
        binding.liButtonInLi.setOnClickListener {
            showProgressBar()
//            auth.signOut()
//            loginUser()
        }
    }
//    private fun loginUser() {
//        val email = binding.eInputInLi.text.toString().trim()
//        val password = binding.pInputInLi.text.toString().trim()
//        if (email.isNotEmpty() && password.isNotEmpty()) {//TODO USE REGEX
//            CoroutineScope(Dispatchers.IO).launch {
//                try {
//                    auth.signInWithEmailAndPassword(email, password).await()
//                    withContext(Dispatchers.Main) {
//                        finishLoginUser()
//                    }
//                } catch (e: Exception) {
//                    withContext(Dispatchers.Main) {
//                        hideProgressBar()
//                        Toast.makeText(this@LogIn, e.message, Toast.LENGTH_LONG).show()
//                    }
//                }
//            }
//        } else {
//            Toast.makeText(this@LogIn, "* fields are required", Toast.LENGTH_LONG).show()
//            hideProgressBar()
//        }
//    }
    private fun finishLoginUser()  {
        val intent =Intent(this,HomePage::class.java)
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