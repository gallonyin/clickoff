package org.caworks.clockoff.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

import org.caworks.clockoff.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({ MainActivity.enterActivity(this) }, 1500)

        findViewById(R.id.activity_main).setOnClickListener { MainActivity.enterActivity(this) }
    }
}
