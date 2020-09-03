package br.felipe.parrot.domain.repository

import br.felipe.parrot.data.dto.UserDTO
import br.felipe.parrot.domain._config.repository.ParrotRepository

class LoginRemoteRepository: ParrotRepository.Remote() {

    private val api by retrofit<ContactsAPI>()

    suspend fun sendSingUp(body: UserDTO) {
        val result =  api.sendSingUp(body)
    }

}