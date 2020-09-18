package br.felipe.parrot.domain.repository

import androidx.annotation.RequiresPermission
import androidx.annotation.RequiresPermission.*
import br.felipe.parrot.data.dto.login.LoginReceiveUserDTO
import br.felipe.parrot.data.dto.login.LoginSendUserDTO
import br.felipe.parrot.data.dto.logout.LogoutDTO
import br.felipe.parrot.data.dto.main.CreateContactReceiveDTO
import br.felipe.parrot.data.dto.main.CreateContactSendDTO
import br.felipe.parrot.data.dto.signin.SignInReceiveUserDTO
import br.felipe.parrot.data.dto.signin.SignInSendUserDTO
import retrofit2.http.*

interface ContactsAPI {

    // SENT to api
    @POST("signup")
    suspend fun sendSingUp(
        @Body signIn: SignInSendUserDTO
    ): SignInReceiveUserDTO

    @POST("login")
    suspend fun sendLogin(
        @Body login: LoginSendUserDTO
    ): LoginReceiveUserDTO

    @DELETE("logout")
    suspend fun logout(
        @Header("token") token: String
    )

    @POST("contacts")
    suspend fun createContact(
        @Body newContact: CreateContactSendDTO
    ): CreateContactReceiveDTO
}