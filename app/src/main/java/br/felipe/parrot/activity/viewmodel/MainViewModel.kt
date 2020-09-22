package br.felipe.parrot.activity.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.felipe.parrot.activity.listener.ContactClickListener
import br.felipe.parrot.core.ViewState
import br.felipe.parrot.core.util.Event
import br.felipe.parrot.core.util.call
import br.felipe.parrot.data.ui.Contact
import br.felipe.parrot.domain.usecase.LogoutUseCase
import br.felipe.parrot.domain.usecase.ListingContactsUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val listingContactsUseCase: ListingContactsUseCase
): ViewModel(), ContactClickListener {

    override val onContactClicked: MutableLiveData<Event.Data<Contact>>
        get() {
            TODO()
        }

    private val _viewStateLogout = MutableLiveData<ViewState>()
    val viewStateLogout = _viewStateLogout as LiveData<ViewState>

    private val _viewStateListing = MutableLiveData<ViewState>()
    val viewStateListing = _viewStateListing as LiveData<ViewState>

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

    fun listing() = viewModelScope.launch {
        listingContactsUseCase()
            .onStarted {
                _viewStateListing.value = ViewState.LoadingState
        }.onSuccess {
                _viewStateListing.value = ViewState.IdleState
            Log.d("************", "Listing contacts...")
        }.onFailure {
                _viewStateListing.value = ViewState.ErrorState(it)
                Log.d("************", "Listing contacts failing...")
        }.onFinish {

        }.execute()
    }
}