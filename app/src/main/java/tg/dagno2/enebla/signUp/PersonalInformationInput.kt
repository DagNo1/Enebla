package tg.dagno2.enebla.signUp

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import tg.dagno2.enebla.MainActivity
import tg.dagno2.enebla.User
import tg.dagno2.enebla.databinding.ActivityPersonalInformationInputBinding

class PersonalInformationInput : AppCompatActivity() {
    private lateinit var number: String
    // create instance of firebase auth
    private lateinit var auth: FirebaseAuth
    private lateinit var database : DatabaseReference
    private lateinit var binding: ActivityPersonalInformationInputBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
        binding = ActivityPersonalInformationInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")
        number =  intent.getStringExtra("phoneNumber") ?: ""
        binding.suCaNextButton.setOnClickListener {
            showProgressBar()
            val formFilled = valueFiled(binding.fnInputInCa, binding.tv3InCa) + valueFiled(binding.lnInputInCa, binding.tv4InCa) +
                    valueFiled(binding.gender, binding.tv5InCa) + valueFiled(binding.eInputInCa, binding.tv6InCa) +
                    valueFiled(binding.pInputInCa, binding.tv7InCa) + valueFiled(binding.cpInputInCa, binding.tv8InCa)

            if (formFilled != 6) {//all 6 fields are filled
                Toast.makeText(this@PersonalInformationInput, "* fields are required to register.", Toast.LENGTH_LONG).show()
                hideProgressBar()
                return@setOnClickListener
            }
            registerUser()
        }
    }
    private fun valueFiled(v: View, t : TextView): Int{//checks values were filed and marks unfilled ones
        val input : String = t.text.toString()

        var filled = 1

        val nameR = Regex("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*\$")
        val passR = Regex("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}\$")
        val emailR = Regex("([\\w\\.\\-_]+)?\\w+@[\\w-_]+(\\.\\w+){1,}")

        when (v) {
            binding.fnInputInCa ->
                if(!str(binding.fnInputInCa).trim().matches(nameR)) filled = 0
            binding.lnInputInCa ->
                if(!str(binding.lnInputInCa).trim().matches(nameR)) filled = 0
            binding.gender -> {
                val genderChosen = binding.rbMaleInCa.isChecked || binding.rbFemaleInCa.isChecked
                if (!genderChosen) filled = 0
            }
            binding.eInputInCa ->
                if(!str(binding.eInputInCa).trim().matches(emailR)) filled = 0
            binding.pInputInCa ->
                if(!str(binding.pInputInCa).matches(passR)) filled = 0
            binding.cpInputInCa -> {
                if(!str(binding.cpInputInCa).matches(passR) || str(binding.cpInputInCa) != str(binding.pInputInCa)) filled = 0
            }
        }

        if (filled == 0 && input.last() != '*') t.text = "$input*" //to mark the unfilled input with star
        if (filled == 1 && input.last() == '*') t.text = input.substring(0,input.length - 1) //to remove filled input's star
        return filled
    }
    private fun str(input: EditText): String =  input.text.toString() // thought it would make it cleaner str == stringer
    private fun registerUser() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.createUserWithEmailAndPassword(binding.eInputInCa.text.toString(), binding.eInputInCa.text.toString()).await()
                withContext(Dispatchers.Main) {
                    uploadData()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    hideProgressBar()
                    Toast.makeText(this@PersonalInformationInput, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun uploadData(){
        val user = User(str(binding.fnInputInCa), str(binding.lnInputInCa), str(binding.eInputInCa),binding.rbMaleInCa.isChecked,number) // gender true if male false if female
        database.child(auth.currentUser?.uid.toString()).setValue(user).addOnSuccessListener {
            Toast.makeText(this@PersonalInformationInput, "Successfully Registered", Toast.LENGTH_LONG).show()
            val intent =Intent(this,MainActivity::class.java)
            intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }.addOnFailureListener{
            Toast.makeText(this@PersonalInformationInput, "Registration Failed", Toast.LENGTH_LONG).show()
        }
    }
    private fun hideProgressBar(){
        binding.progressBar.isVisible = false
        binding.suCaNextButton.isVisible = true
    }
    private fun showProgressBar(){
        binding.progressBar.isVisible = true
        binding.suCaNextButton.isVisible = false
    }
}
