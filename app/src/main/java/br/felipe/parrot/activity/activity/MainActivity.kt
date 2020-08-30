package br.felipe.parrot.activity.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import br.felipe.parrot.R
import br.felipe.parrot.activity.adapter.ContactsListAdapter
import br.felipe.parrot.activity.fragment.ContactFragment
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_fragment.*

class MainActivity: AppCompatActivity() {

    private val contactsListContacts by lazy { ContactsListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        main_toolbar.setupWithNavController(navController, appBarConfiguration)

        contact_list.adapter = contactsListContacts
    }
}