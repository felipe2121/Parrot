package br.felipe.parrot.activity.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import br.felipe.parrot.R
import br.felipe.parrot.activity.viewmodel.LoginViewModel
import br.felipe.parrot.core.ViewState
import br.felipe.parrot.core.exception.ParrotException
import br.felipe.parrot.domain.usecase.LoginUseCase
import br.felipe.parrot.domain.usecase.LoginUseCase.LoginInputException
import br.felipe.parrot.domain.usecase.LoginUseCase.LoginInputException.LoginInput.EMAIL
import br.felipe.parrot.domain.usecase.LoginUseCase.LoginInputException.LoginInput.PASSWORD
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Console

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        setUpUI()
        subscribeUI()
    }


    private fun setUpUI() {
        login_email_edit_text.addTextChangedListener { login_text_email.error = null }
        login_password_edit_text.addTextChangedListener { login_text_password.error = null }

        login_log_button.setOnClickListener {

            val inputTextEmail = login_text_email.editText?.text.toString()
            val inputTextPassword = login_text_password.editText?.text.toString()

            viewModel.login(inputTextEmail, inputTextPassword)
        }

        login_register_button.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun subscribeUI() = viewModel.apply {

        viewState.observe(this@LoginActivity) {

            when (it) {
                ViewState.LoadingState -> {
                    login_progress_bar.visibility =  View.VISIBLE
                }
                ViewState.EmptyState -> {

                }
                ViewState.IdleState -> {
                    val i = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(i)
                    finish()
                }
                is ViewState.ErrorState -> {

                    val exception: ParrotException = it.error
                    if (exception is LoginInputException) {
                        exception.errors.forEach {
                            if (it.type == EMAIL) {
                                login_text_email.error = getString(R.string.isBlankError)
                            }
                            if (it.type == PASSWORD) {
                                login_text_password.error = getString(R.string.isBlankError)
                            }
                        }
                    } else {
                        Snackbar.make(
                            Container,
                            exception.errorMessage(this@LoginActivity),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    /**Error message*/
    /*// Set error text
    passwordLayout.error = getString(R.string.error)

    // Clear error text
    passwordLayout.error = null */

    /**Snackbar*/
    /*val contextView = findViewById<View>(R.id.context_view)

    snackbar.make(contextView, R.string.text_label, Snackbar.LENGTH_SHORT)
    .show()*/

    /*Snackbar.make(contextView, R.string.text_label, Snackbar.LENGTH_LONG)
    .setAction(R.string.action_text) {
        // Responds to click on the action
    }
    .show()*/
}