package tg.dagno2.enebla.firstTimer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator3
import tg.dagno2.enebla.MainActivity
import tg.dagno2.enebla.R
import tg.dagno2.enebla.logIn.LogIn
import tg.dagno2.enebla.signUp.PhoneNumberInput

class Boarding : AppCompatActivity() {
    private val imagesList = mutableListOf<Int>(R.drawable.ic_profile,R.drawable.ic_friends,R.drawable.ic_restaurant)
    private val headerTextList = mutableListOf<String>("Profile","Friends","Restaurants")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
        setContentView(R.layout.activity_boarding)
        //slide view
        findViewById<ViewPager2>(R.id.viewPager2).adapter = SlideAdapter(imagesList,headerTextList)
        findViewById<ViewPager2>(R.id.viewPager2).orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val indicator: CircleIndicator3 = findViewById(R.id.indicator)
        indicator.setViewPager(findViewById(R.id.viewPager2))
        //change the first time
        val sharedPreferences = getSharedPreferences("LoadUp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putBoolean("first_time",false)
            apply()
        }
        //button jobs
        val registerText: TextView = findViewById(R.id.b_register_button)
        val logInBtn: Button = findViewById(R.id.b_login_button)

        registerText.setOnClickListener {
            startActivity(Intent(this,PhoneNumberInput::class.java))
        }
        logInBtn.setOnClickListener {
            startActivity(Intent(this,LogIn::class.java))
        }
    }
    override fun onBackPressed() {
        val sharedPreferences = getSharedPreferences("LoadUp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putBoolean("first_time",true)
            apply()
        }
        finishAffinity()
    }
}