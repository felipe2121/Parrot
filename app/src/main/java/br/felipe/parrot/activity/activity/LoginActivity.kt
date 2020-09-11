package br.felipe.parrot.activity.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.felipe.parrot.R
import br.felipe.parrot.activity.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.login_activity.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModel<LoginViewModel>()

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

            val inputTextEmail = login_text_email.editText?.text.toString()
            val inputTextPassword = login_text_password.editText?.text.toString()

            login_text_email.error = null
            login_text_password.error = null

            if (inputTextEmail.isBlank()) login_text_email.error = getString(R.string.isBlankError)
            if (inputTextPassword.isBlank()) login_text_password.error = getString(R.string.isBlankError)

            if (!inputTextEmail.isBlank() && inputTextPassword.isBlank()) {


                viewModel.login(inputTextEmail, inputTextPassword)


            }

            /*val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()*/
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