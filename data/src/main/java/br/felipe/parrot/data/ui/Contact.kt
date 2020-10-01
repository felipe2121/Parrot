package br.felipe.parrot.data.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    var id: String = "",
    var userId: String = "",
    var name: String = "",
    var photo: String = "",
    var email: String = "",
    var phone: String = ""
):Parcelable {
    companion object {
        const val CONTACT = "contact"
    }

    var isHeader: Boolean = false
}

/***
 * Implementes uma interface que possui uma funcao
 * que retornar um booleen para saber se é um header ou nao.
 * ***/