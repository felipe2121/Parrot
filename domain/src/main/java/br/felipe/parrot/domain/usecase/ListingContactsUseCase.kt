package br.felipe.parrot.domain.usecase

import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.core.util.map
import br.felipe.parrot.data.dto.main.main.ContactDTO
import br.felipe.parrot.data.toUI
import br.felipe.parrot.domain._config.usecase.ParrotUseCase
import br.felipe.parrot.domain.repository.UserRepository

class ListingContactsUseCase(
    private val userRepository: UserRepository
): ParrotUseCase.Completable<Any, List<ContactDTO>, Unit>() {

    override suspend fun execute(params: Any?): ParrotResult<List<ContactDTO>> {
        return userRepository.getContacts().map {
            it.toUI()
        }
    }
}