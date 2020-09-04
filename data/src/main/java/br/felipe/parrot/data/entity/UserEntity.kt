package br.felipe.parrot.data.entity

import androidx.room.Entity

@Entity(tableName = "users")
data class UserEntity(
    var id: String = "",
    var name: String = "",
    var email: String = "",
    var token: String = ""
)