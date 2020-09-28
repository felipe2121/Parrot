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
import androidx.lifecycle.observe
import br.felipe.parrot.R
import br.felipe.parrot.activity.viewmodel.ContactDetailViewModel
import br.felipe.parrot.data.ui.Contact
import br.felipe.parrot.data.ui.Contact.Companion.CONTACT
import kotlinx.android.synthetic.main.contact_detail.*
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
        menuInflater.inflate(R.menu.menu_contact_detail, menu)
        return true
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
            val it = Intent(this, MainActivity::class.java)
            startActivity(it)
            finish()
            return true
        }

        if (item.itemId == R.id.ic_edit_menu) {
            // edit contact
            val it = Intent(this, ContactEdit::class.java)
            startActivity(it)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupUI() {
        setSupportActionBar(contact_detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

    }

    private fun bindUI(contact: Contact) {
        contact_detail_text_name.editText?.setText(contact.name)
        Log.d("*******", contact.name)
        contact_detail_text_email.editText?.setText(contact.email)
        contact_detail_text_phone.editText?.setText(contact.phone)
    }

    private fun subscribeUI() = viewModel.run {
        contact.observe(this@ContactDetail) {
            bindUI(it)
        }
    }
}













