package br.felipe.parrot.domain.repository

import br.felipe.parrot.data.dao.UserDAO
import br.felipe.parrot.data.dto.login.LoginReceiveUserDTO
import br.felipe.parrot.data.dto.singup.SingUpReceiveUserDTO
import br.felipe.parrot.data.entity.UserEntity
import br.felipe.parrot.data.toEntity
import br.felipe.parrot.domain._config.repository.ParrotRepository

class ParrotLocalRepository(
    private val userDAO: UserDAO
): ParrotRepository.Local() {

    suspend fun getAllDataUser(): UserEntity {
        return userDAO.getAll()
    }

    suspend fun saveSingUpDataUser(singUpUserData: SingUpReceiveUserDTO) {
        userDAO.insertOrUpdate(singUpUserData.toEntity())
    }

    suspend fun saveLoginDataUser(loginUserData: LoginReceiveUserDTO) {
        userDAO.insertOrUpdate(loginUserData.toEntity())
    }
}
