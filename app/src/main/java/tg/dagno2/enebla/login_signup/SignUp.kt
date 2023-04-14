package tg.dagno2.enebla.login_signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import tg.dagno2.enebla.CommonFunctions
import tg.dagno2.enebla.databinding.ActivitySignUpBinding
import tg.dagno2.enebla.form_validator.Validator

class SignUp : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var usernameView: EditText
    private lateinit var emailView: EditText
    private lateinit var passwordView: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usernameView = binding.unInputInCa
        emailView = binding.eInputInCa
        passwordView = binding.pInputInCa
//        auth = FirebaseAuth.getInstance()
//        database = FirebaseDatabase.getInstance().getReference("Users")

        binding.suCaNextButton.setOnClickListener {
            CommonFunctions.showProgressBar(binding.progressBar, binding.suCaNextButton)
            if (!validateSignUpForm()) {
                CommonFunctions.hideProgressBar(binding.progressBar, binding.suCaNextButton)
                return@setOnClickListener
            }
//            registerUser()
        }
    }
    private fun validateSignUpForm(): Boolean{
        val usernameValidator = Validator.username(CommonFunctions.str(usernameView))
        val emailValidator = Validator.email(CommonFunctions.str(emailView))
        val passwordValidator = Validator.password(CommonFunctions.str(passwordView))

        if (!usernameValidator.successful) usernameView.error = usernameValidator.errorMessage
        if (!emailValidator.successful) emailView.error = emailValidator.errorMessage
        if (!passwordValidator.successful) passwordView.error = passwordValidator.errorMessage

        return usernameValidator.successful && emailValidator.successful && passwordValidator.successful
    }
//    private fun registerUser() {
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                auth.createUserWithEmailAndPassword(binding.eInputInCa.text.toString(), binding.eInputInCa.text.toString()).await()
//                withContext(Dispatchers.Main) {
//                    uploadData()
//                }
//            } catch (e: Exception) {
//                withContext(Dispatchers.Main) {
//                    hideProgressBar()
//                    Toast.makeText(this@PersonalInformationInput, e.message, Toast.LENGTH_LONG).show()
//                }
//            }
//        }
//    }
//    private fun uploadData(){
//        val user = User(str(binding.fnInputInCa), str(binding.lnInputInCa), str(binding.eInputInCa),binding.rbMaleInCa.isChecked,number) // gender true if male false if female
//        database.child(auth.currentUser?.uid.toString()).setValue(user).addOnSuccessListener {
//            Toast.makeText(this@PersonalInformationInput, "Successfully Registered", Toast.LENGTH_LONG).show()
//            val intent =Intent(this,MainActivity::class.java)
//            intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//        }.addOnFailureListener{
//            Toast.makeText(this@PersonalInformationInput, "Registration Failed", Toast.LENGTH_LONG).show()
//        }
//    }

}
