package br.felipe.parrot.domain.repository

import android.util.Log
import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.core.util.onSuccess
import br.felipe.parrot.data.dto.login.LoginReceiveUserDTO
import br.felipe.parrot.data.dto.login.LoginSendUserDTO
import br.felipe.parrot.data.dto.logout.LogoutDTO
import br.felipe.parrot.data.dto.main.CreateContactReceiveDTO
import br.felipe.parrot.data.dto.main.CreateContactSendDTO
import br.felipe.parrot.data.dto.signin.SignInReceiveUserDTO
import br.felipe.parrot.data.dto.signin.SignInSendUserDTO
import br.felipe.parrot.domain._config.repository.ParrotRepository

class UserRepository (
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
                Log.d("************", "Sucesso")
            }
    }

    suspend fun createContact(body: CreateContactSendDTO): ParrotResult<CreateContactReceiveDTO> {
        return parrotRemoteRepository.sendCreateContact(body)
            .onSuccess {
                //parrotLocalRepository.saveLoginDataUser(it)
            }
    }
}

















// change to email and password