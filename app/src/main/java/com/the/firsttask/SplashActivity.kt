package com.the.firsttask

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.the.firsttask.ui.movie.MovieDetailsActivity

class SplashActivity : AppCompatActivity() {
    private val splashViewModel : SplasScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_splash)
       // val content: View = findViewById(android.R.id.content)
        val intent = Intent(this@SplashActivity, DrawerActivity::class.java)
        startActivity(intent)
        finish()
//        content.viewTreeObserver.addOnPreDrawListener(
//            object : ViewTreeObserver.OnPreDrawListener {
//                override fun onPreDraw(): Boolean {
//                    return if (splashViewModel.getIsReady()) {
//
//                        content.viewTreeObserver.removeOnPreDrawListener(this)
//                        true
//
//                    } else {
//                        false
//                    }
//                }
//            }
//        )
    }
}