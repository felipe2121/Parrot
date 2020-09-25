package br.felipe.parrot.activity.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.observe
import br.felipe.parrot.R
import br.felipe.parrot.activity.viewmodel.CreateContactViewModel
import br.felipe.parrot.core.ViewState
import br.felipe.parrot.core.exception.ParrotException
import br.felipe.parrot.domain.usecase.CreateContactUseCase
import br.felipe.parrot.domain.usecase.CreateContactUseCase.CreateContactInputException.NewContactInput.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.create_contact.*
import kotlinx.android.synthetic.main.login_activity.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateContact : AppCompatActivity() {

    private val viewModel by viewModel<CreateContactViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_contact)

        setupUI()
        subscribeUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_create_contact, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            val it = Intent(this, MainActivity::class.java)
            startActivity(it)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupUI() {
        setSupportActionBar(add_new_contact_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        create_contact_editText_name.addTextChangedListener {
            create_contact_text_name.error = null
        }
        create_contact_editText_email.addTextChangedListener {
            create_contact_text_email.error = null
        }
        create_contact_editText_phone.addTextChangedListener {
            create_contact_text_phone.error = null
        }

        create_contact_confirm_button.setOnClickListener {

            val inputTextName = create_contact_text_name.editText?.text.toString()
            val inputTextEmail = create_contact_text_email.editText?.text.toString()
            val inputTextPhone = create_contact_text_phone.editText?.text.toString()

            viewModel.createContact(inputTextName, inputTextEmail, inputTextPhone)
        }
    }

    private fun subscribeUI() = viewModel.apply {

        viewState.observe(this@CreateContact) {

            when (it) {
                ViewState.LoadingState -> {
                    create_contact_progress_bar.visibility = View.VISIBLE
                }
                ViewState.EmptyState -> {

                }
                ViewState.IdleState -> {
                    val i = Intent(this@CreateContact, MainActivity::class.java)
                    startActivity(i)
                    finish()
                }
                is ViewState.ErrorState -> {

                    create_contact_progress_bar.visibility = View.GONE

                    val exception: ParrotException = it.error
                    if (exception is CreateContactUseCase.CreateContactInputException) {
                        exception.errors.forEach {
                            if (it.type == NAME) {
                                create_contact_text_name.error = getString(R.string.isBlankError)
                            }
                            if (it.type == EMAIL) {
                                create_contact_text_email.error = getString(R.string.isBlankError)
                            }
                            if (it.type == PHONE) {
                                create_contact_text_phone.error = getString(R.string.isBlankError)
                            }
                        }
                    } else {
                        Snackbar.make(
                            Container,
                            exception.errorMessage(this@CreateContact),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}