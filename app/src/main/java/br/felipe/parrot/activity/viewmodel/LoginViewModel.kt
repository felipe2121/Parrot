package br.felipe.parrot.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.felipe.parrot.data.dto.login.LoginSendUserDTO
import br.felipe.parrot.domain.usecase.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val login: LoginUseCase
    // outra usecase para saber se o usuario ja esta cadastrado
): ViewModel() {


    fun login(inputLoginEmail: String, inputLoginPassword: String) = viewModelScope.launch {

        val loginInput = LoginSendUserDTO(inputLoginEmail, inputLoginPassword)

        login(LoginUseCase.ParamsLogin(loginInput))
            .onStarted {

            }.onSuccess {

            }.onFailure {

            }.execute()
    }
}