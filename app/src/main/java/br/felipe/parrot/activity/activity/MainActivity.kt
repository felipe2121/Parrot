package br.felipe.parrot.activity.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.felipe.parrot.R
import br.felipe.parrot.activity.adapter.ContactsListAdapter
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity: AppCompatActivity() {

    private val contactsListContacts by lazy { ContactsListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        contact_list.adapter = contactsListContacts
    }
}