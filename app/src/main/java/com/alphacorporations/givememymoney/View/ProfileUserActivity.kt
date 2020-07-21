package com.alphacorporations.givememymoney.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.alphacorporations.givememymoney.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*


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

        this.configureBottomView()


    }


    fun initProfie(user: FirebaseUser?) {
       // name_user.text = user.get
    }

    fun configureBottomView() {
        activity_main_bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_list -> {
                    ActivityCompat.startActivity(this, Intent(this, ListDebtActivity::class.java), null)
                }
                R.id.action_add -> {
                    ActivityCompat.startActivity(this, Intent(this, AddDebtActivity::class.java), null)
                }
                R.id.action_profil -> {
                    ActivityCompat.startActivity(this, Intent(this, ProfileUserActivity::class.java), null)
                }
            }
            true
        }
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