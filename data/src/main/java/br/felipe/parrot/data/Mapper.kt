package br.felipe.parrot.data

import br.felipe.parrot.data.dto.login.LoginReceiveUserDTO
import br.felipe.parrot.data.dto.singup.SingUpReceiveUserDTO
import br.felipe.parrot.data.entity.UserEntity

fun SingUpReceiveUserDTO.toEntity() = UserEntity(
    id = this.user.id,
    name = this.user.name,
    email = this.user.email,
    token = this.token
)

fun LoginReceiveUserDTO.toEntity() = UserEntity(
    id = this.user.id,
    name = this.user.name,
    email = this.user.email,
    password = this.user.password,
    token = this.token
)