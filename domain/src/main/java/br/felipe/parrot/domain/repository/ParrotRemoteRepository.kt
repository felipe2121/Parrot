package br.felipe.parrot.domain.repository

import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.core.util.onSuccess
import br.felipe.parrot.data.dto.LoginUserDTO
import br.felipe.parrot.data.dto.UserDTO
import br.felipe.parrot.domain._config.repository.ParrotRepository

class ParrotRemoteRepository: ParrotRepository.Remote() {

    private val api by retrofit<ContactsAPI>()

    suspend fun sendSingUp(body: UserDTO): ParrotResult<LoginUserDTO> {
        // val result =  api.sendSingUp(body)
        return executeRequest(api) {
            sendSingUp(body)
        }
    }

}