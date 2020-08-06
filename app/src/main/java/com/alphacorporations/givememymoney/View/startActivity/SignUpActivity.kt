package com.alphacorporations.givememymoney.View.startActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alphacorporations.givememymoney.Constant.FIREBASE_COLLECTION_ID
import com.alphacorporations.givememymoney.R
import com.alphacorporations.givememymoney.View.listActivity.ListDebtActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.email_edit_text
import kotlinx.android.synthetic.main.activity_login.password_edit_text
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.error_msg


/**
Created by Alph4 le 22/07/2020.
Projet: Give Me My Money
 **/
class SignUpActivity : AppCompatActivity() {

    //PRIVATE VAR
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        password_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length!! <= 5) password_edit_text.error = "Minimum 6 caracteres"
            }
        })


        //Click sur le btn sign up
        btn_sign_up.setOnClickListener {
            error_msg.visibility = View.INVISIBLE
            signUpVerification()
        }
    }


    fun signUpVerification() {
        val email = email_edit_text.text.toString()
        val password = password_edit_text.text.toString()
        val passwordConfirmation = password_edit_text_assurance.text.toString()


        if (password == passwordConfirmation) signUp(email, password)
        else {
            if (email.isEmpty() || !email.contains('@')) {
                error_msg.text = getString(R.string.email_error)
                error_msg.visibility = View.VISIBLE
            } else {
                error_msg.text = getString(R.string.password_error)
                error_msg.visibility = View.VISIBLE
            }
        }

    }


    private fun signUp(email: String, password: String) {


        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = FirebaseAuth.getInstance().currentUser
                        FIREBASE_COLLECTION_ID = user!!.uid
                        setUserData(user)
                        // Sign in success, update UI with the signed-in user's information
                        startActivity(Intent(this, ListDebtActivity::class.java))
                        finish()
                    } else {
                        error_msg.text = getString(R.string.error_sign_up)
                        error_msg.visibility = View.VISIBLE
                    }
                }
    }

    private fun setUserData(user: FirebaseUser) {

        val data = hashMapOf(
                "pseudo" to pseudo_edit_text.text.toString(),
                "email" to user.email.toString(),
                "birthDate" to "",
                "Country" to this.resources.configuration.locale.country
        )

        db.collection(FIREBASE_COLLECTION_ID).document("UserID")
                .set(data)
                .addOnSuccessListener { finish() }
                .addOnFailureListener { e ->
                    Log.w(Context.ACTIVITY_SERVICE, "Error adding document", e)
                    Toast.makeText(this, "Erreur dans l'enregistrement de la dette", Toast.LENGTH_LONG).show()
                }
    }

}