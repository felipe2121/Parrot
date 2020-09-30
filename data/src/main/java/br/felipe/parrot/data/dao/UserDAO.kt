package br.felipe.parrot.data.dao

import androidx.room.Dao
import androidx.room.Query
import br.felipe.parrot.data._config.dao.DAO
import br.felipe.parrot.data.entity.UserEntity

@Dao
abstract class UserDAO: DAO<UserEntity>() {

    @Query("SELECT * FROM users")
    abstract suspend fun getUser(): UserEntity

    @Query("DELETE FROM users")
    abstract suspend fun deleteDataUser()
}