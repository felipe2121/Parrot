package br.felipe.parrot.domain.repository

import br.felipe.parrot.data.dto.ContactsDTO
import retrofit2.Call
import retrofit2.http.GET

interface ContactsAPI {

    // GET da api
    @GET("")
    fun searchContacts(

    ): Call<ContactsDTO>
}