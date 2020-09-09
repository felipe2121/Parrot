package br.felipe.parrot.domain.repository

import br.felipe.parrot.data.dto.login.LoginReceiveUserDTO
import br.felipe.parrot.data.dto.login.LoginSendUserDTO
import br.felipe.parrot.data.dto.singup.SingUpReceiveUserDTO
import br.felipe.parrot.data.dto.singup.SingUpSendUserDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface ContactsAPI {

    // SENT to api
    @POST("signup")
    suspend fun sendSingUp(
        @Body singUp: SingUpSendUserDTO
    ): SingUpReceiveUserDTO

    @POST("login")
    suspend fun sendLogin(
        @Body login: LoginSendUserDTO
    ): LoginReceiveUserDTO
}