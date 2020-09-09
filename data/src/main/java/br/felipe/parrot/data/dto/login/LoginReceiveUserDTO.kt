package br.felipe.parrot.data.dto.login

data class LoginReceiveUserDTO (
    var user: LoginUserResponseDTO,
    var token: String = ""
)