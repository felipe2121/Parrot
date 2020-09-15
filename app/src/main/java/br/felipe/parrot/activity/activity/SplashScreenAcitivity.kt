package br.felipe.parrot.activity.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import br.felipe.parrot.R
import kotlinx.coroutines.*
import java.util.*
import kotlin.concurrent.schedule

class SplashScreenAcitivity: AppCompatActivity() {

    val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen_activity)

        activityScope.launch {
            delay(1500)
            val intent = Intent(this@SplashScreenAcitivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }
}