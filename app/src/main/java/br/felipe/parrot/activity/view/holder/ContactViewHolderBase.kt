package br.felipe.parrot.activity.view.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.felipe.parrot.activity.view.listener.ContactClickListener
import br.felipe.parrot.data.ui.Contact

abstract class ContactViewHolderBase(view: View): RecyclerView.ViewHolder(view) {
    abstract fun bind(contact: Contact, clickListener: ContactClickListener?)
}