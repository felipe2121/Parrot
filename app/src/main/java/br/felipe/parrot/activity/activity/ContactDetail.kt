package br.felipe.parrot.activity.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.observe
import br.felipe.parrot.R
import br.felipe.parrot.activity.viewmodel.ContactDetailViewModel
import br.felipe.parrot.core.ViewState
import br.felipe.parrot.core.exception.ParrotException
import br.felipe.parrot.data.ui.Contact
import br.felipe.parrot.data.ui.Contact.Companion.CONTACT
import br.felipe.parrot.domain.usecase.ContactEditUseCase
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.contact_detail.*
import kotlinx.android.synthetic.main.contact_detail.edit_contact_cancel_button
import kotlinx.android.synthetic.main.contact_detail.edit_contact_save_button
import kotlinx.android.synthetic.main.contact_edit.*
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.coroutines.NonCancellable.cancel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ContactDetail : AppCompatActivity() {

    private val viewModel by viewModel<ContactDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_detail)

        setupUI()
        subscribeUI()

        if (savedInstanceState == null) {
            viewModel.setContact(intent?.extras?.get(CONTACT) as Contact)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (viewModel.editScreen) {
            menuInflater.inflate(R.menu.menu_edit_contact, menu)
            return true
        } else {
            menuInflater.inflate(R.menu.menu_contact_detail, menu)
            return true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            val it = Intent(this, MainActivity::class.java)
            startActivity(it)
            finish()
            return true
        }

        if (item.itemId == R.id.ic_delete_menu) {
            // delete contact
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.alert_dialog_title))
                .setNegativeButton(resources.getString(R.string.alert_dialog_negative)) { dialog, which ->
                    // Respond to negative button press
                    dialog.cancel()
                }
                .setPositiveButton(resources.getString(R.string.alert_dialog_positive)) { dialog, which ->
                    // Respond to positive button press
                    viewModel.deleteContact()
                }
                .show()
            // viewModel.deleteContact()
        }

        if (item.itemId == R.id.ic_edit_menu) {
            // edit contact

            viewModel.editScreen = true

            if (viewModel.editScreen) {
                contact_detail_text_name.isEnabled = true
                contact_detail_text_email.isEnabled = true
                contact_detail_text_phone.isEnabled = true

                edit_contact_save_button.visibility = View.VISIBLE
                edit_contact_cancel_button.visibility = View.VISIBLE

                contact_detail_toolbar.setTitle(R.string.contact_edit_name_toolbar)

            }
            invalidateOptionsMenu()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupUI() {

        setSupportActionBar(contact_detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        /* if (viewModel.editScreen) { }
        if (!viewModel.editScreen) { } */

        create_contact_editText_name.addTextChangedListener {
            contact_detail_text_name.error = null
        }
        create_contact_editText_email.addTextChangedListener {
            contact_detail_text_email.error = null
        }
        create_contact_editText_phone.addTextChangedListener {
            contact_detail_text_phone.error = null
        }

        edit_contact_save_button.setOnClickListener {

            val inputTextName = contact_detail_text_name.editText?.text.toString()
            val inputTextEmail = contact_detail_text_email.editText?.text.toString()
            val inputTextPhone = contact_detail_text_phone.editText?.text.toString()

            viewModel.updateContact(inputTextName, inputTextEmail, inputTextPhone)
        }

        edit_contact_cancel_button.setOnClickListener {
            viewModel.editScreen = false

            contact_detail_text_name.isEnabled = false
            contact_detail_text_email.isEnabled = false
            contact_detail_text_phone.isEnabled = false

            edit_contact_save_button.visibility = View.GONE
            edit_contact_cancel_button.visibility = View.GONE

            contact_detail_toolbar.setTitle(R.string.contact_detail_name_toolbar)

            invalidateOptionsMenu()
        }
    }

    private fun bindUI(contact: Contact) {

        contact_detail_text_name.editText?.setText(contact.name)
        contact_detail_text_email.editText?.setText(contact.email)
        contact_detail_text_phone.editText?.setText(contact.phone)

        viewModel.contactId = contact.id
    }

    private fun subscribeUI() = viewModel.apply {

        contact.observe(this@ContactDetail) {
            bindUI(it)
        }

        viewState.observe(this@ContactDetail) {
            when (it) {
                ViewState.LoadingState -> {
                    contact_edit_progress.visibility = View.VISIBLE
                }
                ViewState.EmptyState -> {

                }
                ViewState.IdleState -> {
                    Log.d("**********", "SUCESS")
                    contact_edit_progress.visibility = View.GONE
                    val i = Intent(this@ContactDetail, MainActivity::class.java)
                    startActivity(i)
                    finish()
                }
                is ViewState.ErrorState -> {
                    Log.d("**********", "FALING")

                    contact_edit_progress.visibility = View.GONE

                    val exception: ParrotException = it.error
                    if (exception is ContactEditUseCase.EditContactInputException) {
                        exception.errors.forEach {
                            if (it.type == ContactEditUseCase.EditContactInputException.EditContactInput.NAME) {
                                contact_edit_text_name.error = getString(R.string.isBlankError)
                            }
                            if (it.type == ContactEditUseCase.EditContactInputException.EditContactInput.EMAIL) {
                                contact_edit_text_email.error = getString(R.string.isBlankError)
                            }
                            if (it.type == ContactEditUseCase.EditContactInputException.EditContactInput.PHONE) {
                                contact_edit_text_phone.error = getString(R.string.isBlankError)
                            }
                        }
                    } else {
                        Snackbar.make(
                            Container,
                            exception.errorMessage(this@ContactDetail),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        viewStateDeleteContact.observe(this@ContactDetail) {
            when (it) {
                ViewState.LoadingState -> {
                    contact_edit_progress.visibility = View.VISIBLE
                }
                ViewState.EmptyState -> {

                }
                ViewState.IdleState -> {
                    Log.d("**********", "DELETE SUCESS")
                    contact_edit_progress.visibility = View.GONE
                    val i = Intent(this@ContactDetail, MainActivity::class.java)
                    startActivity(i)
                    finish()
                }
                is ViewState.ErrorState -> {
                    Log.d("**********", "DELETE FALING")

                    contact_edit_progress.visibility = View.GONE

                    val exception: ParrotException = it.error
                    Snackbar.make(
                        Container,
                        exception.errorMessage(this@ContactDetail),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}













