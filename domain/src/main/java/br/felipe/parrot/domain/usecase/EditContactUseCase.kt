package br.felipe.parrot.domain.usecase

import br.felipe.parrot.core.exception.ParrotException
import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.core.util.StringWrapper
import br.felipe.parrot.data.dto.main.detailcontact.ContactUpdateDTO
import br.felipe.parrot.data.dto.main.main.ContactDTO
import br.felipe.parrot.domain.R
import br.felipe.parrot.domain._config.usecase.ParrotUseCase
import br.felipe.parrot.domain.repository.ParrotRepository
import br.felipe.parrot.domain.usecase.EditContactUseCase.EditContactInputException.*

class EditContactUseCase(
    private val parrotRepository: ParrotRepository
): ParrotUseCase.Completable<EditContactUseCase.ParamsUpdateContact, ContactDTO, Unit>() {

    data class ParamsUpdateContact(
        val updateContact: ContactUpdateDTO
    )

    class EditContactInputException(val errors: List<ErrorType>): ParrotException() {
        enum class EditContactInput{ NAME, EMAIL, PHONE }
        data class ErrorType(val type: EditContactInput, val message: StringWrapper)
    }

    override suspend fun execute(params: ParamsUpdateContact?): ParrotResult<ContactDTO> {

        val errors = mutableListOf<ErrorType>()

        if(params == null) return ParrotResult.failure(ParrotException())

        if(params.updateContact.name.isBlank()) errors.add(ErrorType(EditContactInput.NAME, StringWrapper(R.string.isBlank_error)))
        if(params.updateContact.email.isBlank()) errors.add(ErrorType(EditContactInput.EMAIL, StringWrapper(R.string.isBlank_error)))
        if(params.updateContact.phone.isBlank()) errors.add(ErrorType(EditContactInput.PHONE, StringWrapper(R.string.isBlank_error)))

        val id: String = ""
        return if(errors.isEmpty()) parrotRepository.getUpdateContact(id, params.updateContact)
        else ParrotResult.failure(EditContactInputException(errors))
    }
}