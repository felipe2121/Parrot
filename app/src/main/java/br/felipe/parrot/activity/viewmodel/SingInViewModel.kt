package br.felipe.parrot.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.felipe.parrot.data.dto.login.LoginSendUserDTO
import br.felipe.parrot.data.dto.signin.SignInSendUserDTO
import br.felipe.parrot.domain.usecase.SignInUseCase
import kotlinx.coroutines.launch

class SingInViewModel(
    private val singIn: SignInUseCase
): ViewModel() {

    private fun signIn(inputSignInName: String, inputSignInEmail: String, inputSignInPassword: String) = viewModelScope.launch {

        val signInInput = SignInSendUserDTO(inputSignInName, inputSignInEmail, inputSignInPassword)

        singIn.execute(SignInUseCase.ParamsSignIn(signInInput))
    }
}