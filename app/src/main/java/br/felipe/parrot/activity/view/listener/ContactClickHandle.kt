package br.felipe.parrot.activity.view.listener

import android.content.Context
import android.content.Intent
import br.felipe.parrot.activity.activity.ContactDetail
import br.felipe.parrot.activity.activity.ContactEdit
import br.felipe.parrot.data.ui.Contact
import br.felipe.parrot.data.ui.Contact.Companion.CONTACT

interface ContactClickHandle {

    fun Context.handleArticleClick(contacts: Contact) {
        Intent(this, ContactEdit::class.java).also { intent ->
            intent.putExtra(CONTACT, contacts)
            startActivity(intent)
        }
    }
}