package br.felipe.parrot.domain.repository

import br.felipe.parrot.data.dao.UserDAO
import br.felipe.parrot.data.dto.LoginUserDTO
import br.felipe.parrot.data.dto.UserDTO
import br.felipe.parrot.data.entity.UserEntity
import br.felipe.parrot.domain._config.repository.ParrotRepository
import kotlinx.coroutines.flow.Flow

class ParrotLocalRepository(
    private val userDAO: UserDAO
): ParrotRepository.Local() {

    suspend fun getAllDataUser(): UserEntity {
        return userDAO.getAll()
    }

    suspend fun saveDataUser(loginUserData: LoginUserDTO) {
        userDAO.insertOrUpdate(loginUserData) // to.Entity
    }
}
