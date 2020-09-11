package br.felipe.parrot.data.dto.signin

data class SignInReceiveUserDTO (
    var user: SignInUserResponseDTO = SignInUserResponseDTO(),
    var token: String = ""
)