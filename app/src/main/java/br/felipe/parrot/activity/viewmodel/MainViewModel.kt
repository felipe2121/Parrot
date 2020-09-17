package br.felipe.parrot.activity.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.felipe.parrot.core.ViewState
import br.felipe.parrot.domain.usecase.LogoutUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val logoutUseCase: LogoutUseCase
): ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState = _viewState as LiveData<ViewState>

    fun logout() = viewModelScope.launch {

        logoutUseCase()
            .onStarted {
                _viewState.value = ViewState.LoadingState
            }.onSuccess {
                _viewState.value = ViewState.IdleState
            }.onFailure {
                _viewState.value = ViewState.ErrorState(it)
            }.onFinish {

            }.execute()
    }
}