package br.felipe.parrot.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.felipe.parrot.domain.usecase.LogoutUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val logoutUseCase: LogoutUseCase
): ViewModel() {

    fun logout() = viewModelScope.launch {

        logoutUseCase()
            .onStarted {

            }.onSuccess {

            }.onFailure {

            }.onFinish {

            }.execute()
    }
}