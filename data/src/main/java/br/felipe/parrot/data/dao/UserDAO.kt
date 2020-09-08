package br.felipe.parrot.data.dao

import androidx.room.Dao
import androidx.room.Query
import br.felipe.parrot.data._config.dao.DAO
import br.felipe.parrot.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserDAO: DAO<UserEntity>() {

    @Query("SELECT * FROM users")
    abstract suspend fun getAll(): UserEntity

}