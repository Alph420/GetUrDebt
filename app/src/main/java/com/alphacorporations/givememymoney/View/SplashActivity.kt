package com.alphacorporations.givememymoney.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alphacorporations.givememymoney.R
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            //TODO move to listDebt
        } else {
            //TODO remplir les fields et executer l'authentification
            FirebaseAuth.getInstance().signInWithEmailAndPassword("rene.jennequin@sfr.fr","admin123")
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                           //TODO move to listDebt
                        }
                    }
        }




    }
}