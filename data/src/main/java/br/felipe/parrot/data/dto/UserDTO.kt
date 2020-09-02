package br.felipe.parrot.data.dto

// recipe everything
data class UserDTO (

    // @SerializedName("nome") var name: String

    // Can be null
    var name: String? = null,
    var email: String? = null,
    var password: String? = null
)