package br.felipe.parrot.data.dto.singup

data class SingUpReceiveUserDTO (
    var user: SingUpUserResponseDTO = SingUpUserResponseDTO(),
    var token: String = ""
)