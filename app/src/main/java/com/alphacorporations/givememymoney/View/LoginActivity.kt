package com.alphacorporations.givememymoney.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alphacorporations.givememymoney.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

/**
Created by Alph4 le 10/07/2020.
Projet: Give Me My Money
 **/

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val intent = Intent(this, ListDebtActivity::class.java)

        bottom_login.setOnClickListener {
            val email = email_edit_text.text.toString()
            val password = password_edit_text.text.toString()

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            startActivity(intent)
                        }
                        else{

                        }
                    }

        }

    }

}