package br.felipe.parrot.domain.usecase

import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.data.dto.singup.SingUpSendUserDTO
import br.felipe.parrot.domain._config.usecase.ParrotUseCase
import br.felipe.parrot.domain.repository.UserRepository
import br.felipe.parrot.domain.usecase.SingUpUseCase.ParamsSingUp

class SingUpUseCase (
    private val userRepository: UserRepository
): ParrotUseCase.Completable<ParamsSingUp, SingUpSendUserDTO,Void>() {

    data class ParamsSingUp(val name: String, val email: String, val password: String)

    override suspend fun execute(params: ParamsSingUp?): ParrotResult<SingUpSendUserDTO> {
        return super.execute(params)
    }
}