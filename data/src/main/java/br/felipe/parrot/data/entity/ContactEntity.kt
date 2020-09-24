package br.felipe.parrot.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey var id: String = "",
    var userId: String = "",
    var name: String =  "",
    var photo: String = "",
    var email: String = "",
    var phone: String = ""
)