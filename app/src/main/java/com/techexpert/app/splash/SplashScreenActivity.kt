package com.techexpert.app.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.airbnb.lottie.LottieAnimationView
import com.techexpert.app.R
import com.techexpert.app.main.MainActivity
import java.time.Clock
import java.time.Instant
import java.time.temporal.ChronoUnit

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // We set the OnExitAnimationListener to customize our splash screen animation.
        // This will allow us to take over the splash screen removal animation.
        splashScreen.setOnExitAnimationListener { vp ->
            val lottieView = findViewById<LottieAnimationView>(R.id.animationView)
            lottieView.enableMergePathsForKitKatAndAbove(true)

            // We compute the delay to wait for the end of the splash screen icon
            // animation.
            val splashScreenAnimationEndTime =
                Instant.ofEpochMilli(vp.iconAnimationStartMillis + vp.iconAnimationDurationMillis)
            val delay = Instant.now(Clock.systemUTC()).until(
                splashScreenAnimationEndTime,
                ChronoUnit.MILLIS
            )

            // Once the delay expires, we start the lottie animation
            lottieView.postDelayed({
                vp.view.alpha = 0f
                vp.iconView.alpha = 0f
                lottieView!!.playAnimation()
            }, delay)

            // Finally we dismiss display our app content using a
            // nice circular reveal
            lottieView.addAnimatorListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
//                    val contentView = findViewById<View>(android.R.id.content)
//                    val imageView = findViewById<ImageView>(R.id.imageView)
//
//                    val animator = ViewAnimationUtils.createCircularReveal(
//                        imageView,
//                        contentView.width / 2,
//                        contentView.height / 2,
//                        0f,
//                        Integer.max(contentView.width, contentView.height).toFloat()
//                    ).setDuration(600)
//
//                    imageView.visibility = View.VISIBLE
//                    animator.start()

                    startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    finish()
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}
