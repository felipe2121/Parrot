package br.felipe.parrot.data.dto.signin

// recipe everything
data class SignInSendUserDTO (

    // @SerializedName("nome") var name: String

    // Can be null
    var name: String = "",
    var email: String = "",
    var password: String = ""
)