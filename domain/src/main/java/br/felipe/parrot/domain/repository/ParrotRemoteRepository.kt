package br.felipe.parrot.domain.repository

import android.util.Log
import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.data.dto.login.LoginReceiveUserDTO
import br.felipe.parrot.data.dto.login.LoginSendUserDTO
import br.felipe.parrot.data.dto.main.createcontact.CreateContactReceiveDTO
import br.felipe.parrot.data.dto.main.createcontact.CreateContactSendDTO
import br.felipe.parrot.data.dto.main.detailcontact.ContactUpdateDTO
import br.felipe.parrot.data.dto.main.main.ContactDTO
import br.felipe.parrot.data.dto.signin.SignInReceiveUserDTO
import br.felipe.parrot.data.dto.signin.SignInSendUserDTO
import br.felipe.parrot.domain._config.repository.ParrotRepository

class ParrotRemoteRepository: ParrotRepository.Remote() {

    private val api by retrofit<ContactsAPI>()

    suspend fun sendLogin(body: LoginSendUserDTO): ParrotResult<LoginReceiveUserDTO> {
        return executeRequest(api) {
            sendLogin(body)
        }
    }

    suspend fun logout(header: String): ParrotResult<Any> {
        return executeRequest(api) {
            logout(header)
        }
    }

    suspend fun sendSignUp(body: SignInSendUserDTO): ParrotResult<SignInReceiveUserDTO> {
        // val result =  api.sendSingUp(body)
        return executeRequest(api) {
            sendSingUp(body)
        }
    }

    suspend fun sendCreateContact(header: String, body: CreateContactSendDTO): ParrotResult<CreateContactReceiveDTO> {
        return executeRequest(api) {
            createContact(header, body)
        }
    }

    suspend fun getContactsUser(token: String): ParrotResult<List<ContactDTO>> {
        return executeRequest(api) {
            getContacts(token)
        }
    }

    suspend fun sendUpdateContact(contactId: String, token: String, body: ContactUpdateDTO): ParrotResult<ContactDTO> {
        Log.d("*****4", contactId)
        return executeRequest(api){
            updateContact(contactId, token, body)
        }
    }

    suspend fun deleteContact(contactId: String, token: String): ParrotResult<Any> {
        return executeRequest(api) {
            deleteContact(contactId, token)
        }
    }
}