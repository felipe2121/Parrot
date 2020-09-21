package br.felipe.parrot.activity.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import br.felipe.parrot.R
import br.felipe.parrot.activity.application.DeleteDataBase
import br.felipe.parrot.activity.viewmodel.MainViewModel
import br.felipe.parrot.core.ViewState
import br.felipe.parrot.core.exception.ParrotException
import br.felipe.parrot.core.util.observeEvent
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity: AppCompatActivity() {

    private val logoutViewModel by viewModel<MainViewModel>()
    private val logout by inject<DeleteDataBase>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setupUI()
        subscribeUI()

        logoutViewModel.listing()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.action_logout) {
            logoutViewModel.logout()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupUI() {
        setSupportActionBar(main_toolbar)

        floating_action_button.setOnClickListener {
            val i = Intent(this, CreateContact::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun subscribeUI() = logoutViewModel.apply {

        eventLogout.observeEvent(this@MainActivity) {
            lifecycleScope.launch {
                logout.inValidate() /* Changed */
                val i = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(i)
            }
        }

        viewState.observe(this@MainActivity) {

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

        viewStateListing.observe(this@MainActivity) {

            when(it) {
                ViewState.LoadingState -> {

                }
                ViewState.EmptyState -> {

                }
                ViewState.IdleState -> {
                    Log.d("**********", "Listing complete!")
                }
                is ViewState.ErrorState -> {

                    Log.d("**********", "Listing fail")

                    val exception: ParrotException = it.error
                    Snackbar.make(Container, exception.errorMessage(this@MainActivity), Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}






















