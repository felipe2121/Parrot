package br.felipe.parrot.activity.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.felipe.parrot.core.ViewState
import br.felipe.parrot.data.dto.main.detailcontact.ContactUpdateDTO
import br.felipe.parrot.data.ui.Contact
import br.felipe.parrot.domain.usecase.ContactDeleteUseCase
import br.felipe.parrot.domain.usecase.ContactEditUseCase
import br.felipe.parrot.domain.usecase.ContactEditUseCase.*
import kotlinx.coroutines.launch

class ContactDetailViewModel(
    private val editContactUseCase: ContactEditUseCase,
    private val contactDeleteUseCase: ContactDeleteUseCase
): ViewModel() {

    var contactId: String = ""

    private val _viewState = MutableLiveData<ViewState>()
    val viewState = _viewState as LiveData<ViewState>

    fun updateContact(inputUpdateContactName: String,
                      inputUpdateContactEmail: String,
                      inputUpdateContactPhone: String) = viewModelScope.launch {

        Log.d("*****1", contactId)

        val updateContactInput = ContactUpdateDTO(inputUpdateContactName, inputUpdateContactEmail, inputUpdateContactPhone)
        editContactUseCase(ParamsUpdateContact(updateContactInput, contactId))
            .onStarted {
                _viewState.value = ViewState.LoadingState
            }.onSuccess {
                _viewState.value = ViewState.IdleState
            }.onFailure {
                _viewState.value = ViewState.ErrorState(it)
            }.onFinish {

            }.execute()

    }

    private val _viewStateDeleteContact = MutableLiveData<ViewState>()
    val viewStateDeleteContact = _viewStateDeleteContact as LiveData<ViewState>

    fun deleteContact() = viewModelScope.launch {

        contactDeleteUseCase(ContactDeleteUseCase.ParamsDeleteContact(contactId))
            .onStarted {
                _viewStateDeleteContact.value = ViewState.LoadingState
            }.onSuccess {
                _viewStateDeleteContact.value = ViewState.IdleState
            }.onFailure {
                _viewStateDeleteContact.value = ViewState.ErrorState(it)
            }.onFinish {

            }.execute()
    }

    /*** Change state of screen ***/
    var editScreen: Boolean = false

    private val _contact = MutableLiveData<Contact>()
    val contact = _contact as LiveData<Contact>

    fun setContact(contact: Contact) {
        _contact.value = contact
    }
}