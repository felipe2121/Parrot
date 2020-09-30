package br.felipe.parrot.data.dao

import androidx.room.Dao
import androidx.room.Query
import br.felipe.parrot.data._config.dao.DAO
import br.felipe.parrot.data.entity.ContactEntity
import br.felipe.parrot.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ContactDAO: DAO<ContactEntity>() {

    @Query("SELECT * FROM contacts ORDER BY name ASC")
    abstract fun getAllContacts(): Flow<List<ContactEntity>>

    @Query("DELETE FROM contacts WHERE id = :contactId")
    abstract suspend fun deleteContact(contactId: String)

    @Query("DELETE FROM contacts")
    abstract suspend fun deleteAllDataContact()
}