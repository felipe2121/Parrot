package br.felipe.parrot.data

import br.felipe.parrot.data.dto.LoginUserDTO
import br.felipe.parrot.data.entity.UserEntity

fun LoginUserDTO.toEntity() = UserEntity(
    id = this.user.id,
    name = this.user.name,
    email = this.user.email,
    token = this.token
)