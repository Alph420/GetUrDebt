package com.alphacorporations.givememymoney.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alphacorporations.givememymoney.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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

        if(email.isEmpty() || password.isEmpty()){ btn_sign_up.Ac}


        //Click sur le btn login
        bottom_login.setOnClickListener {
            val email = email_edit_text.text.toString()
            val password = password_edit_text.text.toString()

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        //Si connexion est bonne start l'app
                        if (task.isSuccessful) {
                            startActivity(intent)
                        }
                        //Sinon retour a l'ecran d'enregistrement
                        else {
                            error_msg.visibility = View.VISIBLE
                        }
                    }
        }

        btn_sign_up.setOnClickListener {
            val email = email_edit_text.text.toString()
            val password = password_edit_text.text.toString()



            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            println("createUserWithEmail:success")
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            println("createUserWithEmail:failure")
                            Toast.makeText(baseContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
        }
    }
}
