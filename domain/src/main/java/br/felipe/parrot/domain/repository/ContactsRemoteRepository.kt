package br.felipe.parrot.domain.repository

import br.felipe.parrot.domain._config.repository.Repository

class ContactsRemoteRepository: Repository.Remote() {

    private val api by retrofit<ContactsAPI>()



}