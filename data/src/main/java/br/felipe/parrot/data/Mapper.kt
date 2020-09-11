package br.felipe.parrot.data

import br.felipe.parrot.data.dto.login.LoginReceiveUserDTO
import br.felipe.parrot.data.dto.signin.SignInReceiveUserDTO
import br.felipe.parrot.data.entity.UserEntity

fun SignInReceiveUserDTO.toEntity() = UserEntity(
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