package br.felipe.parrot.activity.viewmodel

import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.*
import br.felipe.parrot.activity.view.listener.ContactClickListener
import br.felipe.parrot.core.ViewState
import br.felipe.parrot.core.util.Event
import br.felipe.parrot.core.util.SingleMediatorLiveData
import br.felipe.parrot.core.util.call
import br.felipe.parrot.data.ui.Contact
import br.felipe.parrot.domain.usecase.LogoutUseCase
import br.felipe.parrot.domain.usecase.ListingContactsUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val listingContactsUseCase: ListingContactsUseCase
): ViewModel(), ContactClickListener {

    override val onContactClicked = MutableLiveData<Event.Data<Contact>>()

    private val _contacts = SingleMediatorLiveData<List<Contact>>().apply {
        viewModelScope.launch {
            this@apply.emit(listingContactsUseCase.liveResult().asLiveData())
        }
    }

    val contacts = _contacts as LiveData<List<Contact>>

    private val _viewStateListing = MutableLiveData<ViewState>()
    val viewStateListing = _viewStateListing as LiveData<ViewState>

    fun listing() = viewModelScope.launch {
        listingContactsUseCase()
            .onStarted {
                _viewStateListing.value = ViewState.LoadingState
        }.onSuccess {
                _viewStateListing.value = ViewState.IdleState
        }.onFailure {
                _viewStateListing.value = ViewState.ErrorState(it)
        }.onFinish {

        }.execute()
    }

    private val _viewStateLogout = MutableLiveData<ViewState>()
    val viewStateLogout = _viewStateLogout as LiveData<ViewState>

    val eventLogout = MutableLiveData<Event.Callback>()
    fun logout() = viewModelScope.launch {

        logoutUseCase()
            .onStarted {
                _viewStateLogout.value = ViewState.LoadingState
            }.onSuccess {
                _viewStateLogout.value = ViewState.IdleState
                eventLogout.call()
            }.onFailure {
                _viewStateLogout.value = ViewState.ErrorState(it)
            }.onFinish {

            }.execute()
    }
}