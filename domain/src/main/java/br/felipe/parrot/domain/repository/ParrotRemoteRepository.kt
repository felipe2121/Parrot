package br.felipe.parrot.domain.repository

import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.data.dto.login.LoginReceiveUserDTO
import br.felipe.parrot.data.dto.login.LoginSendUserDTO
import br.felipe.parrot.data.dto.singup.SingUpReceiveUserDTO
import br.felipe.parrot.data.dto.singup.SingUpSendUserDTO
import br.felipe.parrot.domain._config.repository.ParrotRepository

class ParrotRemoteRepository: ParrotRepository.Remote() {

    private val api by retrofit<ContactsAPI>()

    suspend fun sendSingUp(body: SingUpSendUserDTO): ParrotResult<SingUpReceiveUserDTO> {
        // val result =  api.sendSingUp(body)
        return executeRequest(api) {
            sendSingUp(body)
        }
    }

    suspend fun sendLogin(body: LoginSendUserDTO): ParrotResult<LoginReceiveUserDTO> {
        return executeRequest(api) {
            sendLogin(body)
        }
    }
}