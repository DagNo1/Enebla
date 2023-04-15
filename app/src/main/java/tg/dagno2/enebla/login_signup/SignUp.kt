package tg.dagno2.enebla.login_signup

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import tg.dagno2.enebla.CommonFunctions
import tg.dagno2.enebla.CommonFunctions.Companion.str
import tg.dagno2.enebla.HomePage
import tg.dagno2.enebla.databinding.ActivitySignUpBinding
import tg.dagno2.enebla.entities.User
import tg.dagno2.enebla.form_validator.Validator

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var usernameView: EditText
    private lateinit var emailView: EditText
    private lateinit var passwordView: EditText
    private lateinit var termsView: CheckBox
    private val db: DatabaseReference = FirebaseDatabase
        .getInstance("https://enebla-c65cd-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users")
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usernameView = binding.unInputInCa
        emailView = binding.eInputInCa
        passwordView = binding.pInputInCa
        termsView = binding.termAgreement

        binding.suCaNextButton.setOnClickListener {
            CommonFunctions.disableFields(listOf(usernameView,emailView,passwordView,termsView))
            CommonFunctions.showProgressBar(binding.progressBar, binding.suCaNextButton)
            if (!validSignUpForm()) {
                CommonFunctions.hideProgressBar(binding.progressBar, binding.suCaNextButton)
                CommonFunctions.enableFields(listOf(usernameView,emailView,passwordView,termsView))
                return@setOnClickListener
            }
            registerUser()
        }
    }
    private fun validSignUpForm(): Boolean{
        val usernameValidator = Validator.username(str(usernameView))
        val emailValidator = Validator.email(str(emailView))
        val passwordValidator = Validator.password(str(passwordView))
        val termsValidator = Validator.terms(termsView.isChecked)

        usernameView.error = usernameValidator.errorMessage
        emailView.error = emailValidator.errorMessage
        passwordView.error = passwordValidator.errorMessage
        termsView.error = termsValidator.errorMessage

        return usernameValidator.successful && emailValidator.successful && passwordValidator.successful && termsValidator.successful
    }
    private fun registerUser() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.createUserWithEmailAndPassword(binding.eInputInCa.text.toString(), binding.pInputInCa.text.toString()).await()
                val usernameUpdate = UserProfileChangeRequest.Builder().setDisplayName(binding.unInputInCa.toString()).build()
                auth.currentUser?.updateProfile(usernameUpdate)?.await()
                withContext(Dispatchers.Main) {
                    uploadUserData()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    CommonFunctions.hideProgressBar(binding.progressBar, binding.suCaNextButton)
                    CommonFunctions.enableFields(listOf(usernameView,emailView,passwordView,termsView))
                    Toast.makeText(this@SignUp, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun uploadUserData() {
        val user = User(str(binding.unInputInCa), str(binding.eInputInCa))
        val uid = auth.currentUser?.uid.toString()
        println("________________________________333333333333333333333333333333333")
        db.child(uid).setValue(user).addOnCompleteListener {
            if (!it.isSuccessful) {
                auth.currentUser!!.delete()
                throw Exception("Registration Failed")
            }
        }
        Toast.makeText(this@SignUp, "Successfully Registered", Toast.LENGTH_LONG).show()
        val intent = Intent(this, HomePage::class.java)
        intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
