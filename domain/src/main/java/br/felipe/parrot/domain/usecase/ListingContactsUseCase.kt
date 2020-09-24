package br.felipe.parrot.domain.usecase

import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.core.util.map
import br.felipe.parrot.data.dto.main.main.ContactDTO
import br.felipe.parrot.data.toUI
import br.felipe.parrot.data.ui.Contact
import br.felipe.parrot.domain._config.usecase.ParrotUseCase
import br.felipe.parrot.domain.repository.ParrotRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ListingContactsUseCase(
    private val parrotRepository: ParrotRepository
): ParrotUseCase.Completable<Unit, List<ContactDTO>, Flow<List<Contact>>>() {

    override suspend fun liveResult(params: Unit?) = flow {
        emitAll(parrotRepository.getContactsDatabase().map {
            it.toUI()
        })
    }

    override suspend fun execute(params: Unit?): ParrotResult<List<ContactDTO>> {
        return parrotRepository.getContacts()
    }
}