package br.felipe.parrot.domain.repository

import br.felipe.parrot.data.dto.login.LoginReceiveUserDTO
import br.felipe.parrot.data.dto.login.LoginSendUserDTO
import br.felipe.parrot.data.dto.main.createcontact.CreateContactReceiveDTO
import br.felipe.parrot.data.dto.main.createcontact.CreateContactSendDTO
import br.felipe.parrot.data.dto.main.detailcontact.ContactUpdateDTO
import br.felipe.parrot.data.dto.main.main.ContactDTO
import br.felipe.parrot.data.dto.main.main.ContactResponseDTO
import br.felipe.parrot.data.dto.signin.SignInReceiveUserDTO
import br.felipe.parrot.data.dto.signin.SignInSendUserDTO
import retrofit2.http.*

interface ContactsAPI {

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
        @Header("token") token: String,
        @Body newContact: CreateContactSendDTO
    ): CreateContactReceiveDTO

    @GET("contacts")
    suspend fun getContacts(
        @Header("token") token: String
    ): List<ContactDTO>

    @PUT("contacts/{id}")
    suspend fun updateContact(
        @Path("id") contactId: String,
        @Header("token") token: String,
        @Body contactUpdate: ContactUpdateDTO
    ): ContactDTO

    @DELETE("contacts/{id}")
    suspend fun deleteContact(
        @Path("id") contactId: String,
        @Header("token") token: String
    )
}