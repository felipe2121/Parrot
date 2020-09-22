package br.felipe.parrot.activity.listener

import androidx.lifecycle.MutableLiveData
import br.felipe.parrot.core.util.Event
import br.felipe.parrot.data.ui.Contact

interface ContactClickListener {

    val onContactClicked: MutableLiveData<Event.Data<Contact>>

    fun onClickArticle (article: Contact) {
        onContactClicked.value = Event.Data(article)
    }
}