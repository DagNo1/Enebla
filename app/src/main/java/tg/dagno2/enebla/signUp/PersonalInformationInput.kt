package tg.dagno2.enebla.signUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import tg.dagno2.enebla.MainActivity
import tg.dagno2.enebla.R

class PersonalInformationInput : AppCompatActivity() {
    private lateinit var fName: EditText
    private lateinit var lName: EditText
    private lateinit var male: RadioButton
    private lateinit var female: RadioButton
    private lateinit var email: EditText
    private lateinit var pass: EditText
    private lateinit var cpass: EditText
    private lateinit var next: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null) {
            supportActionBar?.hide()
        }
        setContentView(R.layout.activity_personal_information_input)

        fName = findViewById(R.id.fn_input_in_ca)
        lName = findViewById(R.id.ln_input_in_ca)
        male = findViewById(R.id.radioButton_male_in_ca)
        female = findViewById(R.id.radioButton_female_in_ca)
        email = findViewById(R.id.e_input_in_ca)
        pass = findViewById(R.id.p_input_in_ca)
        cpass = findViewById(R.id.cp_input_in_ca)
        next = findViewById(R.id.su_ca_next_button)

        next.setOnClickListener {
            var formFilled = valueFiled(fName,findViewById(R.id.tv3_in_ca))
            formFilled = valueFiled(lName,findViewById(R.id.tv4_in_ca)) && formFilled
            formFilled = valueFiled(male,findViewById(R.id.tv5_in_ca))  && formFilled
            formFilled = valueFiled(female,findViewById(R.id.tv5_in_ca)) && formFilled
            formFilled = valueFiled(email,findViewById(R.id.tv6_in_ca)) && formFilled
            formFilled = valueFiled(pass,findViewById(R.id.tv7_in_ca)) && formFilled
            formFilled = valueFiled(cpass,findViewById(R.id.tv8_in_ca)) && formFilled
            if (formFilled) {
                startActivity(Intent(this,MainActivity::class.java))
            }
        }
    }

    private fun valueFiled(v: View, t : TextView): Boolean{//checks values were filed and marks unfilled ones
        var filled = true
        val nameR = Regex("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*\$")
        val passR = Regex("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}\$")
        val emailR = Regex("([\\w\\.\\-_]+)?\\w+@[\\w-_]+(\\.\\w+){1,}")
        val text = t.text.toString()
        when (v) {
            fName -> if(!fName.text.toString().matches(nameR)) filled = false
            lName -> if(!lName.text.toString().matches(nameR)) filled = false
            male,female -> if (!(male.isChecked || female.isChecked)) filled = false
            email -> if(!email.text.toString().matches(emailR)) filled = false
            pass -> if(!pass.text.toString().matches(passR)) filled = false
            cpass -> if(!cpass.text.toString().matches(passR) && text == pass.text.toString()) filled = false
        }
        //to mark the unfilled input with star
        if (!filled && text.last() != '*') t.text = "$text*"
        //to remove filled input's star
        if (filled && text.last() == '*') t.text = text.substring(0,text.length - 1)
        return filled
    }
}
