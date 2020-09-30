package br.felipe.parrot.domain.repository

import br.felipe.parrot.data.dao.ContactDAO
import br.felipe.parrot.data.dao.UserDAO
import br.felipe.parrot.data.dto.login.LoginReceiveUserDTO
import br.felipe.parrot.data.dto.main.main.ContactDTO
import br.felipe.parrot.data.dto.signin.SignInReceiveUserDTO
import br.felipe.parrot.data.entity.ContactEntity
import br.felipe.parrot.data.entity.UserEntity
import br.felipe.parrot.data.toEntity
import br.felipe.parrot.data.ui.Contact
import br.felipe.parrot.domain._config.repository.ParrotRepository
import kotlinx.coroutines.flow.Flow

class ParrotLocalRepository(
    private val userDAO: UserDAO,
    private val contactDAO: ContactDAO
): ParrotRepository.Local() {

    suspend fun getAllDataUser(): UserEntity {
        return userDAO.getUser()
    }

    suspend fun deleteDataUser(): Any {
        return userDAO.deleteDataUser()
    }

    suspend fun deleteContactsUser(): Any {
        return contactDAO.deleteAllDataContact()
    }

    suspend fun getAllContacts(): Flow<List<ContactEntity>> {
        return contactDAO.getAllContacts()
    }

    suspend fun getToken(): String {
        return userDAO.getUser().token
    }

    suspend fun saveSingUpDataUser(singUpUserData: SignInReceiveUserDTO) {
        userDAO.insertOrUpdate(singUpUserData.toEntity())
    }

    suspend fun saveLoginDataUser(loginUserData: LoginReceiveUserDTO) {
        userDAO.insertOrUpdate(loginUserData.toEntity())
    }

    suspend fun saveContacts(contacts: List<ContactDTO>) {
        contactDAO.insertOrUpdate(contacts.toEntity())
    }

    suspend fun deleteContact(contactId: String) {
        contactDAO.deleteContact(contactId)
    }
}
