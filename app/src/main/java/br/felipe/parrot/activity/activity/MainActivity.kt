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

        setupUI()

        // contact_list.adapter = contactsListContacts
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }

        if(item.itemId == R.id.action_out) {
            Intent(this, RegisterActivity::class.java).also {
                startActivity(it)
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupUI() {
        setSupportActionBar(main_toolbar)

        floating_action_button.setOnClickListener {
            val i = Intent(this, NewContact::class.java)
            startActivity(i)
            finish()
        }

    }
}






















