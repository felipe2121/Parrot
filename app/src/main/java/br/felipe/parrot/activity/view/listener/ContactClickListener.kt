package br.felipe.parrot.activity.view.listener

import androidx.lifecycle.MutableLiveData
import br.felipe.parrot.core.util.Event
import br.felipe.parrot.data.ui.Contact

interface ContactClickListener {

    val onContactClicked: MutableLiveData<Event.Data<Contact>>

    fun onClickContact (contact: Contact) {
        onContactClicked.value = Event.Data(contact)
    }
}