package br.felipe.parrot.domain.repository

import br.felipe.parrot.data.dto.LoginUserDTO
import br.felipe.parrot.data.dto.UserDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface ContactsAPI {

    // SENT to api
    @POST("signup")
    suspend fun sendSingUp(
        @Body singUp: UserDTO
    ): LoginUserDTO

}