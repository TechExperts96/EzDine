package com.techexpert.ezdine.app.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.techexpert.ezdine.app.R
import com.techexpert.ezdine.app.main.MainActivity

class SplashScreen2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen2)

        Handler().postDelayed(
            Runnable {
                // Intent is used to switch from one activity to another.
                val i = Intent(this@SplashScreen2Activity, MainActivity::class.java)
                startActivity(i) // invoke the SecondActivity.
                finish() // the current activity will get finished.
            },
            3000
        )
    }
}
