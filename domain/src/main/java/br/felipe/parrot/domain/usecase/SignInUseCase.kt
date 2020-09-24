package br.felipe.parrot.domain.usecase

import br.felipe.parrot.core.exception.ParrotException
import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.core.util.StringWrapper
import br.felipe.parrot.data.dto.signin.SignInReceiveUserDTO
import br.felipe.parrot.data.dto.signin.SignInSendUserDTO
import br.felipe.parrot.domain.R
import br.felipe.parrot.domain._config.usecase.ParrotUseCase
import br.felipe.parrot.domain.repository.ParrotRepository
import br.felipe.parrot.domain.usecase.SignInUseCase.ParamsSignIn
import br.felipe.parrot.domain.usecase.SignInUseCase.SignInInputException.ErrorType
import br.felipe.parrot.domain.usecase.SignInUseCase.SignInInputException.SignInput.*

class SignInUseCase(
    private val parrotRepository: ParrotRepository
) : ParrotUseCase.Completable<ParamsSignIn, SignInReceiveUserDTO, Unit>() {

    data class ParamsSignIn(
        val userInputSignIn: SignInSendUserDTO
    )

    class SignInInputException(val errors: List<ErrorType>) : ParrotException() {
        enum class SignInput { NAME, EMAIL, PASSWORD, CONFIRM_PASSWORD, DEFERENCE_PASSWORD }
        data class ErrorType(val type: SignInput, val message: StringWrapper)
    }

    override suspend fun execute(params: ParamsSignIn?): ParrotResult<SignInReceiveUserDTO> {

        val errors = mutableListOf<ErrorType>()

        if (params == null) return ParrotResult.failure(ParrotException())

        if (params.userInputSignIn.name.isBlank()) errors.add(
            ErrorType(
                NAME,
                StringWrapper(R.string.isBlank_error)
            )
        )

        if (params.userInputSignIn.email.isBlank()) errors.add(
            ErrorType(
                EMAIL,
                StringWrapper(R.string.isBlank_error)
            )
        )

        if (params.userInputSignIn.password.isBlank()) errors.add(
            ErrorType(
                PASSWORD,
                StringWrapper(R.string.isBlank_error)
            )
        )

        if (params.userInputSignIn.confirmPassword?.isBlank() == true) errors.add(
            ErrorType(
                CONFIRM_PASSWORD,
                StringWrapper(R.string.isBlank_error)
            )
        )

        if (params.userInputSignIn.password != params.userInputSignIn.confirmPassword) errors.add(
            ErrorType(DEFERENCE_PASSWORD, StringWrapper(R.string.password_deference))
        )

        return if (errors.isEmpty()) parrotRepository.signIn((params.userInputSignIn))
        else ParrotResult.failure(SignInInputException(errors))
    }
}