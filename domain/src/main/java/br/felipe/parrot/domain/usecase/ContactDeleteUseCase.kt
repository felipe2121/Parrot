package br.felipe.parrot.domain.usecase

import br.felipe.parrot.core.exception.ParrotException
import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.data.dto.main.detailcontact.ContactUpdateDTO
import br.felipe.parrot.data.dto.main.main.ContactDTO
import br.felipe.parrot.domain._config.usecase.ParrotUseCase
import br.felipe.parrot.domain.repository.ParrotRepository
import br.felipe.parrot.domain.usecase.ContactDeleteUseCase.*

class ContactDeleteUseCase(
    private val parrotRepository: ParrotRepository
): ParrotUseCase.Completable<ParamsDeleteContact, Any, Unit>() {

    data class ParamsDeleteContact(
        val id: String
    )

    override suspend fun execute(params: ParamsDeleteContact?): ParrotResult<Any> {

        if(params == null) return ParrotResult.failure(ParrotException())

        else return parrotRepository.deleteContact(params.id)
    }
}