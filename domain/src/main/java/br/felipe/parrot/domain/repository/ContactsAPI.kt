package br.felipe.parrot.domain.repository

import br.felipe.parrot.data.dto.ContactsDTO
import retrofit2.Call
import retrofit2.http.GET

interface ContactsAPI {

    // GET da api
    @GET("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
            ".eyJ1c2VyIjoiNWVkYWExZDNkODI1YmIzZjgzYmRiMzRkIiwiaWF0IjoxNTkxMzg2NTc5fQ" +
            ".W-F0zh-8H1DhSukoCIMj53twSMYY7QwoQrbveSEhRXA")
    fun searchContacts(

    ): Call<ContactsDTO>
}