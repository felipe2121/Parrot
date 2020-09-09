package br.felipe.parrot.data.dto.singup

// recipe everything
data class SingUpSendUserDTO (

    // @SerializedName("nome") var name: String

    // Can be null
    var name: String = "",
    var email: String = "",
    var password: String = ""
)