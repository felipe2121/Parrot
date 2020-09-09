package br.felipe.parrot.domain.usecase

import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.data.dto.login.LoginSendUserDTO
import br.felipe.parrot.domain._config.usecase.ParrotUseCase
import br.felipe.parrot.domain.repository.UserRepository
import br.felipe.parrot.domain.usecase.LoginUseCase.*

class LoginUseCase (
    private val userRepository: UserRepository
):ParrotUseCase.Completable<ParamsLogin, LoginSendUserDTO, Unit>() {

    data class ParamsLogin(val name: String, val password: String)

    override suspend fun execute(params: ParamsLogin?): ParrotResult<LoginSendUserDTO> {
        return super.execute(params)
    }
}












