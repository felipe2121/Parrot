package br.felipe.parrot.domain.usecase

import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.domain._config.usecase.ParrotUseCase
import br.felipe.parrot.domain.repository.ParrotRepository

class LogoutUseCase(
    private val parrotRepository: ParrotRepository
): ParrotUseCase.Completable<Any, Any, Unit>() {

    override suspend fun execute(params: Any?): ParrotResult<Any> {
        return parrotRepository.logout()
    }
}