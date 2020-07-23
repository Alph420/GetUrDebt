package com.alphacorporations.givememymoney.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alphacorporations.givememymoney.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
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

        val intent = Intent(this, ListDebtActivity::class.java)


        //Click sur le btn sign up
        btn_sign_up.setOnClickListener {
            val pseudo = peusdo_edit_text.text.toString()
            val email = email_edit_text.text.toString()
            val password = password_edit_text.text.toString()
            val passwordConfirmation = password_edit_text_assurance.text.toString()

            verification()

            if (valide) signUp(email, password)
            else{
                error_msg.text = "Email deja utiliser"
                error_msg.visibility = View.VISIBLE
            }


        }
    }

    //Verification if existing user with the same email or pseudo existing in databse
    fun verification() {
    }


    fun signUp(email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        println("createUserWithEmail:success")
                        startActivity(intent)
                        finish()
                    } else {
                        error_msg.text = getString(R.string.error_sign_up)
                        error_msg.visibility = View.VISIBLE
                    }
                }
    }

}