package br.felipe.parrot.domain.usecase

import br.felipe.parrot.core.exception.ParrotException
import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.core.util.StringWrapper
import br.felipe.parrot.data.dto.login.LoginReceiveUserDTO
import br.felipe.parrot.data.dto.login.LoginSendUserDTO
import br.felipe.parrot.domain.R
import br.felipe.parrot.domain._config.usecase.ParrotUseCase
import br.felipe.parrot.domain.repository.UserRepository
import br.felipe.parrot.domain.usecase.LoginUseCase.*
import br.felipe.parrot.domain.usecase.LoginUseCase.SignInInputException.ErrorType
import br.felipe.parrot.domain.usecase.LoginUseCase.SignInInputException.LoginInput.EMAIL
import br.felipe.parrot.domain.usecase.LoginUseCase.SignInInputException.LoginInput.PASSWORD

class LoginUseCase (
    private val userRepository: UserRepository
):ParrotUseCase.Completable<ParamsLogin, LoginReceiveUserDTO, Unit>() {

    data class ParamsLogin(
        val userInputLogin: LoginSendUserDTO
    )

    class SignInInputException (val error: List<ErrorType>): ParrotException() {
        enum class LoginInput{ EMAIL, PASSWORD }
        data class ErrorType(val type: LoginInput, val message: StringWrapper)
    }

    override suspend fun execute(params: ParamsLogin?): ParrotResult<LoginReceiveUserDTO> {

        val errors = mutableListOf<ErrorType>()

        if(params == null) return ParrotResult.failure(ParrotException())

        if(params.userInputLogin.email.isBlank()) errors.add(ErrorType(EMAIL, StringWrapper(R.string.isBlank_error)))

        if(params.userInputLogin.password.isBlank()) errors.add(ErrorType(PASSWORD, StringWrapper(R.string.isBlank_error)))

        return if(errors.isEmpty()) userRepository.login(params.userInputLogin)
        else ParrotResult.failure(SignInInputException(errors))
    }
}












