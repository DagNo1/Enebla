package tg.dagno2.enebla

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import tg.dagno2.enebla.firstTimer.Boarding
import tg.dagno2.enebla.logIn.LogIn

class SplashScreen : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
        auth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_splash_screen)
        val sharedPreferences = getSharedPreferences("LoadUp", Context.MODE_PRIVATE)
        //TODO CHECK IF THE USER IS SIGNED IN
        Handler().postDelayed({
            if (sharedPreferences.getBoolean("first_time",true)){
                val onBoarding = Intent(this, Boarding::class.java)
                startActivity(onBoarding)
            } else {
                if (auth.currentUser == null) startActivity(Intent(this, LogIn::class.java))
                else startActivity(Intent(this, MainActivity::class.java))
            }
            finish()
        }, 1000)
    }
}