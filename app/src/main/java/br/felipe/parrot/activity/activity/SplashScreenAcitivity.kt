package br.felipe.parrot.activity.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import br.felipe.parrot.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule

class SplashScreenAcitivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen_activity)

        val SPLASHSCREEN_DELAY: Long = 1500

        val handler = Handler()
        handler.postDelayed({
            val it = Intent(this, LoginActivity::class.java)
            startActivity(it)
            finish()
        }, SPLASHSCREEN_DELAY)

        /*val timer = Timer()

        timer.schedule(3000) {
            val it = Intent(this@SplashScreenAcitivity, LoginActivity::class.java)
            startActivity(it)
            finish()
        }*/

        /*val activityScope = CoroutineScope(Dispatchers.Main)

        activityScope.launch {
            delay(3000)

            val it = Intent(this@SplashScreenAcitivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }*/

    }
}