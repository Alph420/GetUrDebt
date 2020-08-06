package com.alphacorporations.givememymoney.View.startActivity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alphacorporations.givememymoney.Constant.FIREBASE_COLLECTION_ID
import com.alphacorporations.givememymoney.R
import com.alphacorporations.givememymoney.View.listActivity.ListDebtActivity
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


        //Click sur le btn login
        btn_sign_in.setOnClickListener {
            val email = email_edit_text.text.toString()
            val password = password_edit_text.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                error_msg.text = if (email.isEmpty()) "Veuillez rentrer une adresse email" else "Veuillez rentrer un mot de passe"
                error_msg.visibility = View.VISIBLE
                btn_sign_in.highlightColor = Color.RED
            } else {
                error_msg.visibility = View.INVISIBLE
                connexion(email, password)
            }
        }

        btn_sign_up_activity.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }


    private fun connexion(email: String, password: String) {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    //Si connexion est bonne start l'app
                    if (task.isSuccessful) {
                        val user = FirebaseAuth.getInstance().currentUser
                        FIREBASE_COLLECTION_ID = user!!.uid
                        startActivity(Intent(this, ListDebtActivity::class.java))
                        finish()
                    }
                    //Sinon retour a l'ecran d'enregistrement
                    else {
                        error_msg.text = getString(R.string.error_login)
                        error_msg.visibility = View.VISIBLE
                    }
                }
    }
}
