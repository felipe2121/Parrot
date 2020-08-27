package br.felipe.parrot.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import br.felipe.parrot.R

class SplashScreenAcitivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val SPLASHSCREEN_DELAY: Long = 1000

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val handler = Handler()
        handler.postDelayed({
            val it = Intent(this, ParrotActivity::class.java)
            startActivity(it)
            finish()
        }, SPLASHSCREEN_DELAY)
    }
}