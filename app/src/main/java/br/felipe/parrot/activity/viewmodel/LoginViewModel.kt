package br.felipe.parrot.activity.viewmodel

import androidx.lifecycle.ViewModel
import br.felipe.parrot.domain.usecase.LoginUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase
): ViewModel() {


}