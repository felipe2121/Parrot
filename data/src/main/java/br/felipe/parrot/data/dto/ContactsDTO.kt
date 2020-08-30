package br.felipe.parrot.data.dto

// recipe everything
data class ContactsDTO (

    // @SerializedName("nome") var name: String

    // Can be null
    var name: String? = null,
    var email: String? = null,
    var phone: String? = null
)