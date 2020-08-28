package br.felipe.parrot.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.felipe.parrot.R
import kotlinx.android.synthetic.main.register_activity.*

class RegisterActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        setUpUI()
    }

    private fun setUpUI() {
        setSupportActionBar(register_toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }
}