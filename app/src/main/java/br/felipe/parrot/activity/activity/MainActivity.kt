package br.felipe.parrot.activity.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import br.felipe.parrot.R
import br.felipe.parrot.activity.adapter.ContactsListAdapter
import br.felipe.parrot.activity.adapter.ContactsListAdapter.AdapterLetter.*
import br.felipe.parrot.activity.application.DeleteDataBase
import br.felipe.parrot.activity.view.listener.ContactClickHandle
import br.felipe.parrot.activity.viewmodel.MainViewModel
import br.felipe.parrot.core.ViewState
import br.felipe.parrot.core.exception.ParrotException
import br.felipe.parrot.core.util.observeEvent
import br.felipe.parrot.data.ui.Contact
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_contact_list.*
import kotlinx.coroutines.NonCancellable.cancel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity: AppCompatActivity(), ContactClickHandle {

    private val viewModel by viewModel<MainViewModel>()
    private val logout by inject<DeleteDataBase>()
    private val contactListAdapter by lazy { ContactsListAdapter(FIRST) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setupUI()
        subscribeUI()

        viewModel.listing()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.action_logout) {
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.alert_logout_title))
                .setNegativeButton(resources.getString(R.string.alert_logout_cancel)) { dialog, which ->
                    dialog.cancel()
                }
                .setPositiveButton(resources.getString(R.string.alert_logout_confirm)) { dialog, which ->
                    viewModel.logout()
                }
                .show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupUI() {
        setSupportActionBar(main_toolbar)

        contact_list.adapter = contactListAdapter
        contactListAdapter.contactClickListener = viewModel

        floating_action_button.setOnClickListener {
            val i = Intent(this, CreateContact::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun subscribeUI() = viewModel.apply {

        eventLogout.observeEvent(this@MainActivity) {
            lifecycleScope.launch {
                logout.inValidate() /* Changed */
                val i = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(i)
            }
        }

        viewStateLogout.observe(this@MainActivity) {

            when(it) {
                ViewState.LoadingState -> {

                }
                ViewState.EmptyState -> {

                }
                ViewState.IdleState -> {

                }
                is ViewState.ErrorState -> {
                    val exception: ParrotException = it.error
                    Snackbar.make(Container, exception.errorMessage(this@MainActivity), Snackbar.LENGTH_LONG).show()
                }
            }
        }

        contacts.observe(this@MainActivity) {
            contactListAdapter.refresh(it)
        }

        onContactClicked.observeEvent(this@MainActivity) {
            this@MainActivity.handleArticleClick(it)
        }

        viewStateListing.observe(this@MainActivity) {

            when(it) {
                ViewState.LoadingState -> {
                    progress.visibility = View.VISIBLE
                    if(contactListAdapter.itemCount != 0) {
                        progress.visibility = View.GONE
                    }
                }
                ViewState.EmptyState -> {

                }
                ViewState.IdleState -> {
                    progress.visibility = View.GONE
                    if (contactListAdapter.itemCount == 0) {
                        MaterialAlertDialogBuilder(this@MainActivity)
                            .setTitle(resources.getString(R.string.empty_list))
                            .show()
                    }
                }
                is ViewState.ErrorState -> {
                    val exception: ParrotException = it.error
                    Snackbar.make(Container, exception.errorMessage(this@MainActivity), Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}






















