package br.felipe.parrot.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey var id: String = "",
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var token: String = ""
)