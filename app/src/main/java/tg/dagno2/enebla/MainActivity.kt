package tg.dagno2.enebla

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
        setContentView(R.layout.activity_main)

        val restaurant = Restaurant()
        val friends = Friends()
        val profile = Profile()
        setCurrentFragment(restaurant)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        // handle navigation selection
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.restaurant -> setCurrentFragment(restaurant)
                R.id.friends -> setCurrentFragment(friends)
                R.id.profile -> setCurrentFragment(profile)
            }
            true
        }
    }
    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment,fragment)
            commit()
        }
    }
}