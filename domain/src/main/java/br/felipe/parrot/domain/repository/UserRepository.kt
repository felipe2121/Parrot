package br.felipe.parrot.domain.repository

import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.core.util.onSuccess
import br.felipe.parrot.data.dto.LoginUserDTO
import br.felipe.parrot.data.dto.UserDTO
import br.felipe.parrot.domain._config.repository.ParrotRepository

class UserRepository (
    private val parrotLocalRepository: ParrotLocalRepository,
    private val loginRemoteRepository: ParrotRemoteRepository
): ParrotRepository() {

    suspend fun signIn (body: UserDTO): ParrotResult<LoginUserDTO> {
        return loginRemoteRepository.sendSingUp(body)
            .onSuccess {
                parrotLocalRepository.saveDataUser(it)
            }
    }
}