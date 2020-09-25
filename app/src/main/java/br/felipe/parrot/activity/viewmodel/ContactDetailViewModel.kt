package br.felipe.parrot.activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.felipe.parrot.data.ui.Contact

class ContactDetailViewModel: ViewModel() {

    private val _contact = MutableLiveData<Contact>()
    val contact = _contact as LiveData<Contact>

    fun setContact(contact: Contact) {
        _contact.value = contact
    }
}