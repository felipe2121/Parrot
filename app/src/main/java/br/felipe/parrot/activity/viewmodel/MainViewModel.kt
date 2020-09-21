package br.felipe.parrot.activity.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.felipe.parrot.core.ViewState
import br.felipe.parrot.core.util.Event
import br.felipe.parrot.core.util.call
import br.felipe.parrot.domain.usecase.LogoutUseCase
import br.felipe.parrot.domain.usecase.CreateContactUseCase
import br.felipe.parrot.domain.usecase.ListingContactsUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val listingContactsUseCase: ListingContactsUseCase
): ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState = _viewState as LiveData<ViewState>

    private val _viewStateListing = MutableLiveData<ViewState>()
    val viewStateListing = _viewStateListing as LiveData<ViewState>

    val eventLogout = MutableLiveData<Event.Callback>()
    fun logout() = viewModelScope.launch {

        logoutUseCase()
            .onStarted {
                _viewState.value = ViewState.LoadingState
            }.onSuccess {
                _viewState.value = ViewState.IdleState
                eventLogout.call()
            }.onFailure {
                _viewState.value = ViewState.ErrorState(it)
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
        }.onFinish {

        }.execute()
    }
}