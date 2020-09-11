package br.felipe.parrot.domain.usecase

import br.felipe.parrot.core.exception.ParrotException
import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.data.dto.signin.SignInReceiveUserDTO
import br.felipe.parrot.data.dto.signin.SignInSendUserDTO
import br.felipe.parrot.domain._config.usecase.ParrotUseCase
import br.felipe.parrot.domain.repository.UserRepository
import br.felipe.parrot.domain.usecase.SignInUseCase.ParamsSignIn

class SignInUseCase (
    private val userRepository: UserRepository
): ParrotUseCase.Completable<ParamsSignIn, SignInReceiveUserDTO,Void>() {

    data class ParamsSignIn(
        val userInputSignIn: SignInSendUserDTO
    )

    override suspend fun execute(params: ParamsSignIn?): ParrotResult<SignInReceiveUserDTO> {
        if(params == null) return ParrotResult.failure(ParrotException())
        return userRepository.signIn(params.userInputSignIn)
    }
}