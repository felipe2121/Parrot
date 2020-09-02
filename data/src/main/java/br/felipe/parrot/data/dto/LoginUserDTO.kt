package br.felipe.parrot.data.dto

data class LoginUserDTO (
    var user: UserResponseDTO? = null,
    var token: String? = null
)