package com.alphacorporations.givememymoney.View.startActivity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.alphacorporations.givememymoney.Constant.FIREBASE_COLLECTION_ID
import com.alphacorporations.givememymoney.R
import com.alphacorporations.givememymoney.View.listActivity.ListDebtActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private val SPLAH_SCREEN: Long = 3000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        logo_splash.startAnimation(AnimationUtils.loadAnimation(this, R.anim.top_animation))
        text_splash.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bottom_animation))

        val user = FirebaseAuth.getInstance().currentUser
        val intentSignUp = Intent(this, LoginActivity::class.java)
        val intentList = Intent(this, ListDebtActivity::class.java)

        val handler = Handler()
        val runnable = Runnable {
            //Si l'utilisateur est different de null -> start l'app
            if (user != null) {
                FIREBASE_COLLECTION_ID = user.uid
                startActivity(intentList)
                finish()
            }
            //Sinon retour a l'ecran d'enregistrement
            else {
                startActivity(intentSignUp)
                finish()
            }
        }
        handler.postDelayed(runnable, SPLAH_SCREEN)

    }

}
