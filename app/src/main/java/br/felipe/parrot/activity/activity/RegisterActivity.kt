package br.felipe.parrot.activity.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.observe
import br.felipe.parrot.R
import br.felipe.parrot.activity.viewmodel.SingInViewModel
import br.felipe.parrot.core.ViewState
import br.felipe.parrot.core.exception.ParrotException
import br.felipe.parrot.domain.usecase.SignInUseCase
import br.felipe.parrot.domain.usecase.SignInUseCase.SignInInputException.SignInput.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.android.synthetic.main.register_activity.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private val viewModel by viewModel<SingInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        setupUI()
        subscribeUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_register, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            val it = Intent(this, LoginActivity::class.java)
            startActivity(it)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupUI() {
        setSupportActionBar(register_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        register_name_edit_text.addTextChangedListener { register_text_name.error = null }
        register_email_edit_text.addTextChangedListener { register_text_email.error = null }
        register_password_edit_text.addTextChangedListener { register_text_password.error = null }
        register_confirm_password_edit_text.addTextChangedListener { register_confirm_text_password.error = null }

        register_button.setOnClickListener {

            val registerName = register_text_name.editText?.text.toString()
            val registerEmail = register_text_email.editText?.text.toString()
            val registerPassword = register_text_password.editText?.text.toString()
            val registerPasswordConfirm = register_confirm_text_password.editText?.text.toString()

            viewModel.signIn(registerName, registerEmail, registerPassword, registerPasswordConfirm)

        }
    }

    private fun subscribeUI() = viewModel.apply {

        viewState.observe(this@RegisterActivity) {

            when (it) {
                ViewState.LoadingState -> {
                    Log.d("**********", "Loading...")
                    register_progress_bar.visibility =  View.VISIBLE
                }
                ViewState.EmptyState -> {

                }
                ViewState.IdleState -> {
                    val i = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(i)
                    finish()
                }
                is ViewState.ErrorState -> {
                    val exception: ParrotException = it.error
                    if (exception is SignInUseCase.SignInInputException) {

                        exception.errors.forEach {
                            if (it.type == NAME) register_text_name.error =
                                getString(R.string.isBlankError)

                            if (it.type == EMAIL) register_text_email.error =
                                getString(R.string.isBlankError)

                            if (it.type == PASSWORD) register_text_password.error =
                                getString(R.string.isBlankError)

                            if (it.type == CONFIRM_PASSWORD) register_confirm_text_password.error =
                                getString(R.string.isBlankError)

                            if (it.type == DEFERENCE_PASSWORD) register_text_password.error =
                                getString(R.string.password_deference)
                        }
                    } else {
                        Snackbar.make(
                            Container,
                            exception.errorMessage(this@RegisterActivity),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    /*topAppBar.setNavigationOnClickListener {
        // Handle navigation icon press
    }

    topAppBar.setOnMenuItemClickListener { menuItem ->
        when (menuItem.itemId) {
            R.id.favorite -> {
                // Handle favorite icon press
                true
            }
            R.id.search -> {
                // Handle search icon press
                true
            }
            R.id.more -> {
                // Handle more item (inside overflow menu) press
                true
            }
            else -> false
        }
    }*/
}