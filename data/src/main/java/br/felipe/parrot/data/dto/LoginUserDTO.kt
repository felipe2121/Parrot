package br.felipe.parrot.data.dto

data class LoginUserDTO (
    var user: UserResponseDTO = UserResponseDTO(),
    var token: String = ""
)