package tg.dagno2.enebla

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tg.dagno2.enebla.firstTimer.Boarding
import tg.dagno2.enebla.login_signup.LogIn

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val sharedPreferences = getSharedPreferences("LoadUp", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("first_time",true)){
            val onBoarding = Intent(this, Boarding::class.java)
            onBoarding.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(onBoarding)
            return
        }
//        if (auth.currentUser == null) {
//            val login = Intent(this, LogIn::class.java)
//            login.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(login)
//            return
//        }
        startActivity(Intent(this, HomePage::class.java))
    }
}