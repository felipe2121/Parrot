package br.felipe.parrot.activity.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.felipe.parrot.R
import br.felipe.parrot.activity.viewmodel.SingInViewModel
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.register_activity.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity: AppCompatActivity() {

    private val viewModel by viewModel<SingInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        setupUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_register, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            val it = Intent(this, LoginActivity::class.java)
            startActivity(it)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupUI() {
        setSupportActionBar(register_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        register_button.setOnClickListener {

            val registerName = register_name.editText?.text.toString()
            val registerEmail = register_email.editText?.text.toString()
            val registerPassword = register_password.editText?.text.toString()
            val registerPasswordConfirm = register_confirm_password.editText?.text.toString()

                // Confirm password
                viewModel.signIn(registerName, registerEmail, registerPassword)

        }
    }

    /*topAppBar.setNavigationOnClickListener {
        // Handle navigation icon press
    }

    topAppBar.setOnMenuItemClickListener { menuItem ->
        when (menuItem.itemId) {
            R.id.favorite -> {
                // Handle favorite icon press
                true
            }
            R.id.search -> {
                // Handle search icon press
                true
            }
            R.id.more -> {
                // Handle more item (inside overflow menu) press
                true
            }
            else -> false
        }
    }*/
}