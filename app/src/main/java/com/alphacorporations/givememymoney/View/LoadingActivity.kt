package com.alphacorporations.givememymoney.View

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.alphacorporations.givememymoney.R
import kotlinx.android.synthetic.main.transition_loading.*

/**
Created by Alph4 le 18/07/2020.
Projet: Give Me My Money
 **/

private val SPLAH_SCREEN: Long = 1500

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.transition_loading)
        progressBar.progressDrawable.setColorFilter(
                Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);

        Loading_img.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_animation))
        progressBar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_animation))

        val handler = Handler()
        val runnable = Runnable {
                progressBar.progress += SPLAH_SCREEN.toInt()
            startActivity(Intent(this, ListDebtActivity::class.java))
        }
        handler.postDelayed(runnable, SPLAH_SCREEN)
    }
}