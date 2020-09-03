package br.felipe.parrot.activity.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.felipe.parrot.R
import br.felipe.parrot.data.dto.UserDTO
import br.felipe.parrot.domain.repository.LoginRemoteRepository
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        setUp()

        /*lifecycleScope.launch {
            val response = LoginRemoteRepository().sendSingUp()
        }*/

    }

    private fun setUp() {

        login_log_button.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }

        login_register_button.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    /**Error message*/
    /*// Set error text
    passwordLayout.error = getString(R.string.error)

    // Clear error text
    passwordLayout.error = null */

    /**Snackbar*/
    /*val contextView = findViewById<View>(R.id.context_view)

    Snackbar.make(contextView, R.string.text_label, Snackbar.LENGTH_SHORT)
    .show()*/

    /*Snackbar.make(contextView, R.string.text_label, Snackbar.LENGTH_LONG)
    .setAction(R.string.action_text) {
        // Responds to click on the action
    }
    .show()*/
}