package br.felipe.parrot.domain.usecase

import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.data.dto.logout.LogoutDTO
import br.felipe.parrot.domain._config.usecase.ParrotUseCase
import br.felipe.parrot.domain.repository.UserRepository

class LogoutUseCase(
    private val userRepository: UserRepository
): ParrotUseCase.Completable<Unit, LogoutDTO, Unit>() {

    suspend fun execute(): ParrotResult<Any> {
        return userRepository.logout()
    }
}