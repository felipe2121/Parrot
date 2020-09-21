package br.felipe.parrot.domain.usecase

import br.felipe.parrot.core.exception.ParrotException
import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.core.util.StringWrapper
import br.felipe.parrot.data.dto.main.createcontact.CreateContactReceiveDTO
import br.felipe.parrot.data.dto.main.createcontact.CreateContactSendDTO
import br.felipe.parrot.domain.R
import br.felipe.parrot.domain._config.usecase.ParrotUseCase
import br.felipe.parrot.domain.repository.UserRepository
import br.felipe.parrot.domain.usecase.CreateContactUseCase.CreateContactInputException.ErrorType
import br.felipe.parrot.domain.usecase.CreateContactUseCase.CreateContactInputException.NewContactInput.*
import br.felipe.parrot.domain.usecase.CreateContactUseCase.ParamsCreateContact

class CreateContactUseCase(
    private val userRepository: UserRepository
): ParrotUseCase.Completable<ParamsCreateContact, CreateContactReceiveDTO, Unit>() {

    data class ParamsCreateContact(
        val newContactInput: CreateContactSendDTO
    )

    class CreateContactInputException (val errors: List<ErrorType>): ParrotException() {
        enum class NewContactInput{ NAME, EMAIL, PHONE }
        data class ErrorType(val type: NewContactInput, val message: StringWrapper)
    }

    override suspend fun execute(params: ParamsCreateContact?): ParrotResult<CreateContactReceiveDTO> {

        val errors = mutableListOf<ErrorType>()

        if(params == null) return ParrotResult.failure(ParrotException())

        if(params.newContactInput.name.isBlank()) errors.add(ErrorType(NAME, StringWrapper(R.string.isBlank_error)))
        if(params.newContactInput.email.isBlank()) errors.add(ErrorType(EMAIL, StringWrapper(R.string.isBlank_error)))
        if(params.newContactInput.phone.isBlank()) errors.add(ErrorType(PHONE, StringWrapper(R.string.isBlank_error)))

        return if(errors.isEmpty()) userRepository.createContact(params.newContactInput)
        else ParrotResult.failure(CreateContactInputException(errors))
    }
}
















