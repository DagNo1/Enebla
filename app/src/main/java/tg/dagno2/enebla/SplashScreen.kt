package tg.dagno2.enebla

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import tg.dagno2.enebla.firstTimer.Boarding
import tg.dagno2.enebla.logIn.LogIn

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
        setContentView(R.layout.activity_splash_screen)
        val sharedPreferences = getSharedPreferences("LoadUp", Context.MODE_PRIVATE)
        Handler().postDelayed({
            if (sharedPreferences.getBoolean("first_time",true)){
                println("____________________________________________________________________HERE_____________________________________")
                val onBoarding = Intent(this, Boarding::class.java)
                startActivity(onBoarding)
            } else {
                startActivity(Intent(this, LogIn::class.java))
            }
            finish()
        }, 2000)
    }
}