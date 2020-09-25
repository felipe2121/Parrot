package br.felipe.parrot.data.dto.main.main

import com.google.gson.annotations.SerializedName


data class ContactDTO (
    @SerializedName("_id")
    var id: String? = null,
    var userId: String? = null,
    var name: String? = null,
    var photo: String? = null,
    var email: String? = null,
    var phone: String? = null
)