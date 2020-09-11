package br.felipe.parrot.domain.usecase

import br.felipe.parrot.core.exception.ParrotException
import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.data.dto.login.LoginReceiveUserDTO
import br.felipe.parrot.data.dto.login.LoginSendUserDTO
import br.felipe.parrot.domain.R
import br.felipe.parrot.domain._config.usecase.ParrotUseCase
import br.felipe.parrot.domain.repository.UserRepository
import br.felipe.parrot.domain.usecase.LoginUseCase.*

class LoginUseCase (
    private val userRepository: UserRepository
):ParrotUseCase.Completable<ParamsLogin, LoginReceiveUserDTO, Unit>() {

    data class ParamsLogin(
        val userInputLogin: LoginSendUserDTO
    )

    override suspend fun execute(params: ParamsLogin?): ParrotResult<LoginReceiveUserDTO> {

        if(params == null) return ParrotResult.failure(ParrotException())

        // if(params.userInputLogin == null) return ParrotResult.failure(ParrotException())

        return userRepository.login(params.userInputLogin)
    }
}












