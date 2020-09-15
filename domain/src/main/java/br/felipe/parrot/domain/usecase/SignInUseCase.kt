package br.felipe.parrot.domain.usecase

import br.felipe.parrot.core.exception.ParrotException
import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.core.util.StringWrapper
import br.felipe.parrot.data.dto.signin.SignInReceiveUserDTO
import br.felipe.parrot.data.dto.signin.SignInSendUserDTO
import br.felipe.parrot.domain.R
import br.felipe.parrot.domain._config.usecase.ParrotUseCase
import br.felipe.parrot.domain.repository.UserRepository
import br.felipe.parrot.domain.usecase.SignInUseCase.ParamsSignIn
import br.felipe.parrot.domain.usecase.SignInUseCase.SignInInputException.ErrorType
import br.felipe.parrot.domain.usecase.SignInUseCase.SignInInputException.SignInput.*

class SignInUseCase (
    private val userRepository: UserRepository
): ParrotUseCase.Completable<ParamsSignIn, SignInReceiveUserDTO,Unit>() {

    data class ParamsSignIn(
        val userInputSignIn: SignInSendUserDTO
    )

    class SignInInputException (val errors: List<ErrorType>): ParrotException() {
        enum class SignInput{ NAME, EMAIL, PASSWORD, CONFIRM_PASSWORD }
        data class ErrorType(val type: SignInput, val message: StringWrapper)
    }

    override suspend fun execute(params: ParamsSignIn?): ParrotResult<SignInReceiveUserDTO> {

        val errors = mutableListOf<ErrorType>()

        if(params == null) return ParrotResult.failure(ParrotException())

        if(params.userInputSignIn.name.isBlank()) errors.add(ErrorType(NAME, StringWrapper(R.string.isBlank_error)))

        if(params.userInputSignIn.email.isBlank()) errors.add(ErrorType(EMAIL, StringWrapper(R.string.isBlank_error)))

        if(params.userInputSignIn.password.isBlank()) errors.add(ErrorType(PASSWORD, StringWrapper(R.string.isBlank_error)))

        if(params.userInputSignIn.confirmPassword?.isBlank() == true) errors.add(ErrorType(CONFIRM_PASSWORD, StringWrapper(R.string.isBlank_error)))

        return if(errors.isEmpty()) userRepository.signIn((params.userInputSignIn))
        else ParrotResult.failure(SignInUseCase.SignInInputException(errors))
    }
}