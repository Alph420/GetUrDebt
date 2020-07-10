package com.alphacorporations.givememymoney.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alphacorporations.givememymoney.R
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val user = FirebaseAuth.getInstance().currentUser
        val intentSignUp = Intent(this, LoginActivity::class.java)
        val intentList = Intent(this, ListDebtActivity::class.java)


        if (user != null) {
            startActivity(intentList)
        } else {
            startActivity(intentSignUp)
        }


    }
}