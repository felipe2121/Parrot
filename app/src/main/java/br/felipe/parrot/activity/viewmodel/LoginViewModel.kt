package br.felipe.parrot.activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.felipe.parrot.core.ViewState
import br.felipe.parrot.data.dto.login.LoginSendUserDTO
import br.felipe.parrot.domain.usecase.LoginUseCase
import br.felipe.parrot.domain.usecase.LoginUseCase.ParamsLogin
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
    // another useCase, to know if de user is already login
): ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState = _viewState as LiveData<ViewState>

    fun login(inputLoginEmail: String, inputLoginPassword: String) = viewModelScope.launch {

        val loginInput = LoginSendUserDTO(inputLoginEmail, inputLoginPassword)
        loginUseCase(ParamsLogin(loginInput))
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

















