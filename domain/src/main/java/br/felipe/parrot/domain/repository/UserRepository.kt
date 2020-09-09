package br.felipe.parrot.domain.repository

import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.core.util.onSuccess
import br.felipe.parrot.data.dto.login.LoginReceiveUserDTO
import br.felipe.parrot.data.dto.login.LoginSendUserDTO
import br.felipe.parrot.data.dto.singup.SingUpReceiveUserDTO
import br.felipe.parrot.data.dto.singup.SingUpSendUserDTO
import br.felipe.parrot.domain._config.repository.ParrotRepository

class UserRepository (
    private val parrotLocalRepository: ParrotLocalRepository,
    private val parrotRemoteRepository: ParrotRemoteRepository
): ParrotRepository() {

    suspend fun signIn (body: SingUpSendUserDTO): ParrotResult<SingUpReceiveUserDTO> {
        return parrotRemoteRepository.sendSingUp(body).onSuccess { parrotLocalRepository.saveSingUpDataUser(it) }
    }

    suspend fun login (body: LoginSendUserDTO): ParrotResult<LoginReceiveUserDTO> {
        return parrotRemoteRepository.sendLogin(body).onSuccess { parrotLocalRepository.saveLoginDataUser(it) }
    }
}