package br.felipe.parrot.activity.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import br.felipe.parrot.R
import br.felipe.parrot.activity.adapter.ContactsListAdapter
import br.felipe.parrot.activity.viewmodel.MainViewModel
import br.felipe.parrot.core.ViewState
import br.felipe.parrot.core.exception.ParrotException
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity: AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setupUI()
        subscribeUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.action_logout) {
            viewModel.logout()
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

    private fun subscribeUI() = viewModel.apply {

        viewState.observe(this@MainActivity) {

            when(it) {
                ViewState.LoadingState -> {

                }
                ViewState.EmptyState -> {

                }
                ViewState.IdleState -> {
                    val i = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(i)
                    finish()
                }
                is ViewState.ErrorState -> {
                    val exception: ParrotException = it.error
                    Snackbar.make(Container, exception.errorMessage(this@MainActivity), Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}






















