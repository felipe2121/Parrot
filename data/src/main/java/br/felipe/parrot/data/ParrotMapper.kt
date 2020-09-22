package br.felipe.parrot.data

import br.felipe.parrot.data.dto.login.LoginReceiveUserDTO
import br.felipe.parrot.data.dto.main.main.ContactDTO
import br.felipe.parrot.data.dto.signin.SignInReceiveUserDTO
import br.felipe.parrot.data.entity.UserEntity
import br.felipe.parrot.data.ui.Contact

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

fun List<ContactDTO>.toUI(): List<ContactDTO> {
    return this.map {
        it.toUI()
    }
}

fun ContactDTO.toUI() = ContactDTO(
    id = this.id ?: "",
    userId = this.userId ?: "",
    name = this.name ?: "",
    photo = this.photo ?: "",
    email = this.email ?: "",
    phone = this.phone ?: ""
)