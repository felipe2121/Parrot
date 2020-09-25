package br.felipe.parrot.activity.view.holder

import android.view.View
import br.felipe.parrot.activity.view.listener.ContactClickListener
import br.felipe.parrot.data.ui.Contact
import kotlinx.android.synthetic.main.main_view_holder_header.view.*

class ContactViewHolderHeader(view: View): ContactViewHolderBase(view) {

    override fun bind(contact: Contact, clickListener: ContactClickListener?) {

        with(itemView) {
            setOnClickListener { clickListener?.onClickContact(contact) }

            contact_letter_header.text = contact.name[0].toString()
            contact_name_header.text = contact.name

        }
    }
}