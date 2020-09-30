package br.felipe.parrot.domain.repository

import android.util.Log
import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.core.util.onSuccess
import br.felipe.parrot.data.dto.login.LoginReceiveUserDTO
import br.felipe.parrot.data.dto.login.LoginSendUserDTO
import br.felipe.parrot.data.dto.main.createcontact.CreateContactReceiveDTO
import br.felipe.parrot.data.dto.main.createcontact.CreateContactSendDTO
import br.felipe.parrot.data.dto.main.detailcontact.ContactUpdateDTO
import br.felipe.parrot.data.dto.main.main.ContactDTO
import br.felipe.parrot.data.dto.main.main.ContactResponseDTO
import br.felipe.parrot.data.dto.signin.SignInReceiveUserDTO
import br.felipe.parrot.data.dto.signin.SignInSendUserDTO
import br.felipe.parrot.data.entity.ContactEntity
import br.felipe.parrot.data.ui.Contact
import br.felipe.parrot.domain._config.repository.ParrotRepository
import kotlinx.coroutines.flow.Flow

class ParrotRepository (
    private val parrotLocalRepository: ParrotLocalRepository,
    private val parrotRemoteRepository: ParrotRemoteRepository
): ParrotRepository() {

    suspend fun signIn (body: SignInSendUserDTO): ParrotResult<SignInReceiveUserDTO> {
        return parrotRemoteRepository.sendSignUp(body).onSuccess { parrotLocalRepository.saveSingUpDataUser(it) }
    }


    suspend fun login (body: LoginSendUserDTO): ParrotResult<LoginReceiveUserDTO> {
        return parrotRemoteRepository.sendLogin(body)
            .onSuccess {
                parrotLocalRepository.saveLoginDataUser(it)
            }
    }

    suspend fun logout(): ParrotResult<Any> {
        val token = parrotLocalRepository.getToken()
        return parrotRemoteRepository.logout(token)
            .onSuccess {
                // parrotLocalRepository.deleteDataUser()
                // parrotLocalRepository.deleteContactsUser()
            }
    }

    suspend fun createContact(body: CreateContactSendDTO): ParrotResult<CreateContactReceiveDTO> {
        val token = parrotLocalRepository.getToken()
        return parrotRemoteRepository.sendCreateContact(token, body)
            .onSuccess {
                //parrotLocalRepository.saveLoginDataUser(it)
            }
    }

    suspend fun getContacts(): ParrotResult<List<ContactDTO>> {
        val token = parrotLocalRepository.getToken()
        return parrotRemoteRepository.getContactsUser(token)
            .onSuccess { contactList ->
                parrotLocalRepository.saveContacts(contactList)
            }
    }

    suspend fun getContactsDatabase(): Flow<List<ContactEntity>> {
        return parrotLocalRepository.getAllContacts()
    }

    suspend fun getUpdateContact(id: String, body: ContactUpdateDTO): ParrotResult<ContactDTO> {
        val token = parrotLocalRepository.getToken()
        Log.d("*****3", id)
        return parrotRemoteRepository.sendUpdateContact(id, token, body)
            .onSuccess {}
    }

    suspend fun deleteContact(id: String): ParrotResult<Any> {
        val token = parrotLocalRepository.getToken()
        return parrotRemoteRepository.deleteContact(id, token)
            .onSuccess {
                parrotLocalRepository.deleteContact(id)
            }
    }
}

















// change to email and password