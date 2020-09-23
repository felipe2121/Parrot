package br.felipe.parrot.data.ui

data class Contact(
    var id: String = "",
    var userId: String = "",
    var name: String = "",
    var photo: String = "",
    var email: String = "",
    var phone: String = ""
)

/***
 * Implementes uma interface que possui uma funcao
 * que retornar um booleen para saber se é um header ou nao.
 * ***/