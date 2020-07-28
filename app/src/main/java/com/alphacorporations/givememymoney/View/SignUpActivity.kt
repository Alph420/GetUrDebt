package com.alphacorporations.givememymoney.View

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.alphacorporations.givememymoney.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.email_edit_text
import kotlinx.android.synthetic.main.activity_login.password_edit_text
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.error_msg

/**
Created by Alph4 le 22/07/2020.
Projet: Give Me My Money
 **/
class SignUpActivity : AppCompatActivity() {

    var valide = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        password_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length!! <= 5) password_edit_text.error = "Minimum 6 caracteres"
            }
        })


        //Click sur le btn sign up
        btn_sign_up.setOnClickListener {
            signUpVerification()
        }
    }


    fun signUpVerification() {
        val pseudo = peusdo_edit_text.text.toString()
        val email = email_edit_text.text.toString()
        val password = password_edit_text.text.toString()
        val passwordConfirmation = password_edit_text_assurance.text.toString()


        if (valide && password == passwordConfirmation) signUp(email, password)
        else {
            if (email.isEmpty() || !email.contains('@')) {
                error_msg.text = "Veuillez rentrer une email valide"
                error_msg.visibility = View.VISIBLE
            } else {
                error_msg.text = "Mot de passe non identiques"
                error_msg.visibility = View.VISIBLE
            }

        }

    }


    private fun signUp(email: String, password: String) {


        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        println("createUserWithEmail:success")
                        startActivity(Intent(this, ListDebtActivity::class.java))
                        finish()
                    } else {
                        error_msg.text = getString(R.string.error_sign_up)
                        error_msg.visibility = View.VISIBLE
                    }
                }
    }

}