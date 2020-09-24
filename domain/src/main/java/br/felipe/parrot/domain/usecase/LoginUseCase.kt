package br.felipe.parrot.domain.usecase

import br.felipe.parrot.core.exception.ParrotException
import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.core.util.StringWrapper
import br.felipe.parrot.data.dto.login.LoginReceiveUserDTO
import br.felipe.parrot.data.dto.login.LoginSendUserDTO
import br.felipe.parrot.domain.R
import br.felipe.parrot.domain._config.usecase.ParrotUseCase
import br.felipe.parrot.domain.repository.ParrotRepository
import br.felipe.parrot.domain.usecase.LoginUseCase.*
import br.felipe.parrot.domain.usecase.LoginUseCase.LoginInputException.ErrorType
import br.felipe.parrot.domain.usecase.LoginUseCase.LoginInputException.LoginInput.EMAIL
import br.felipe.parrot.domain.usecase.LoginUseCase.LoginInputException.LoginInput.PASSWORD

class LoginUseCase (
    private val parrotRepository: ParrotRepository
):ParrotUseCase.Completable<ParamsLogin, LoginReceiveUserDTO, Unit>() {

    data class ParamsLogin(
        val userInputLogin: LoginSendUserDTO
    )

    class LoginInputException (val errors: List<ErrorType>): ParrotException() {
        enum class LoginInput{ EMAIL, PASSWORD }
        data class ErrorType(val type: LoginInput, val message: StringWrapper)
    }

    override suspend fun execute(params: ParamsLogin?): ParrotResult<LoginReceiveUserDTO> {

        val errors = mutableListOf<ErrorType>()

        if(params == null) return ParrotResult.failure(ParrotException())

        if(params.userInputLogin.email.isBlank()) errors.add(ErrorType(EMAIL, StringWrapper(R.string.isBlank_error)))

        if(params.userInputLogin.password.isBlank()) errors.add(ErrorType(PASSWORD, StringWrapper(R.string.isBlank_error)))

        return if(errors.isEmpty()) parrotRepository.login(params.userInputLogin)
        else ParrotResult.failure(LoginInputException(errors))
    }
}












