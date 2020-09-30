package br.felipe.parrot.activity.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.felipe.parrot.core.ViewState
import br.felipe.parrot.core.util.onFailure
import br.felipe.parrot.core.util.onSuccess
import br.felipe.parrot.data.dto.login.LoginSendUserDTO
import br.felipe.parrot.data.dto.signin.SignInSendUserDTO
import br.felipe.parrot.domain.usecase.SignInUseCase
import kotlinx.coroutines.launch

//Criar um enum class com name, email, password, confirmPassword
class SingInViewModel(
    private val singInUseCase: SignInUseCase
): ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState = _viewState as LiveData<ViewState>

    fun signIn(inputSignInName: String, inputSignInEmail: String, inputSignInPassword: String, inputSignInConfirmPassword: String)
            = viewModelScope.launch {

        val signInInput = SignInSendUserDTO(inputSignInName, inputSignInEmail, inputSignInPassword, inputSignInConfirmPassword)

        singInUseCase(SignInUseCase.ParamsSignIn(signInInput))
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