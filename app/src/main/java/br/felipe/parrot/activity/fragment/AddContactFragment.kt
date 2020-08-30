package br.felipe.parrot.activity.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.felipe.parrot.R
import br.felipe.parrot.activity.adapter.ContactsListAdapter
import kotlinx.android.synthetic.main.main_fragment.*

class AddContactFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_contact_fragment, container, false)
    }
}