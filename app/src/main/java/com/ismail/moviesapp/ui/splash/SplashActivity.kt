package com.ismail.moviesapp.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ismail.moviesapp.R
import com.ismail.moviesapp.ui.main.MainActivity
import org.jetbrains.anko.intentFor

// TODO: Extend this to BaseActivity, implement SplashNavigator
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI()
        setContentView(R.layout.activity_splash_screen)

        val splashTimeOut = 1000

        Handler().postDelayed({
            startActivity(intentFor<MainActivity>())
            finish()
        }, splashTimeOut.toLong())
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                or View.SYSTEM_UI_FLAG_IMMERSIVE)
    }
}
