package br.felipe.parrot.domain.repository

import android.util.Log
import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.data.dto.login.LoginReceiveUserDTO
import br.felipe.parrot.data.dto.login.LoginSendUserDTO
import br.felipe.parrot.data.dto.logout.LogoutDTO
import br.felipe.parrot.data.dto.signin.SignInReceiveUserDTO
import br.felipe.parrot.data.dto.signin.SignInSendUserDTO
import br.felipe.parrot.domain._config.repository.ParrotRepository
import retrofit2.http.Header

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
}