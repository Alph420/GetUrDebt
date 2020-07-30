package com.alphacorporations.givememymoney.View

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import com.alphacorporations.givememymoney.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_profile_user.*


/**
Created by Alph4 le 19/07/2020.
Projet: Give Me My Money
 **/
class ProfileUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)

        val user = FirebaseAuth.getInstance().currentUser

        initProfie(user)

        log_out_user_profil.setOnClickListener { logOut() }

    }

    fun logOut() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }


    fun initProfie(user: FirebaseUser?) {
        first_name_user.setText(user?.email)
    }


    override fun onResume() {
        super.onResume()
        println("Resume")
    }

    override fun onStart() {
        super.onStart()
        println("Start")
    }
}