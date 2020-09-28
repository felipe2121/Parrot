package br.felipe.parrot.activity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.observe
import br.felipe.parrot.R
import br.felipe.parrot.activity.viewmodel.ContactEditViewModel
import br.felipe.parrot.activity.viewmodel.CreateContactViewModel
import br.felipe.parrot.core.ViewState
import br.felipe.parrot.core.exception.ParrotException
import br.felipe.parrot.domain.usecase.CreateContactUseCase
import br.felipe.parrot.domain.usecase.EditContactUseCase
import br.felipe.parrot.domain.usecase.EditContactUseCase.*
import br.felipe.parrot.domain.usecase.EditContactUseCase.EditContactInputException.EditContactInput.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.contact_detail.*
import kotlinx.android.synthetic.main.contact_edit.*
import kotlinx.android.synthetic.main.create_contact.*
import kotlinx.android.synthetic.main.login_activity.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactEdit : AppCompatActivity() {

    private val viewModel by viewModel<ContactEditViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_edit)

        setupUI()
        subscribeUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit_contact, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupUI() {
        setSupportActionBar(contact_edit_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        contact_edit_editText_name.addTextChangedListener {
            contact_edit_text_name.error = null
        }
        contact_edit_editText_email.addTextChangedListener {
            contact_edit_text_email.error = null
        }
        contact_edit_editText_phone.addTextChangedListener {
            contact_edit_text_phone.error = null
        }

        edit_contact_save_button.setOnClickListener {

            val inputTextName = contact_edit_text_name.editText?.text.toString()
            val inputTextEmail = contact_edit_text_email.editText?.text.toString()
            val inputTextPhone = contact_edit_text_phone.editText?.text.toString()

            viewModel.updateContact(inputTextName, inputTextEmail, inputTextPhone)
        }

        edit_contact_cancel_button.setOnClickListener {
            val i = Intent(this@ContactEdit, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun subscribeUI() = viewModel.apply {

        viewState.observe(this@ContactEdit) {
            when (it) {
                ViewState.LoadingState -> {
                    // create_contact_progress_bar.visibility = View.VISIBLE
                }
                ViewState.EmptyState -> {

                }
                ViewState.IdleState -> {
                    val i = Intent(this@ContactEdit, MainActivity::class.java)
                    startActivity(i)
                    finish()
                }
                is ViewState.ErrorState -> {

                    // create_contact_progress_bar.visibility = View.GONE

                    val exception: ParrotException = it.error
                    if (exception is EditContactInputException) {
                        exception.errors.forEach {
                            if (it.type == NAME) {
                                contact_edit_text_name.error = getString(R.string.isBlankError)
                            }
                            if (it.type == EMAIL) {
                                contact_edit_text_email.error = getString(R.string.isBlankError)
                            }
                            if (it.type == PHONE) {
                                contact_edit_text_phone.error = getString(R.string.isBlankError)
                            }
                        }
                    } else {
                        Snackbar.make(
                            Container,
                            exception.errorMessage(this@ContactEdit),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

    }
}