package br.felipe.parrot.activity.view.holder

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.felipe.parrot.activity.view.listener.ContactClickListener
import br.felipe.parrot.data.ui.Contact
import kotlinx.android.synthetic.main.main_view_holder.view.*
import kotlinx.android.synthetic.main.main_view_holder_header.view.*

class ContactViewHolder(view: View): ContactViewHolderBase(view) {

    override fun bind(contact: Contact, clickListener: ContactClickListener?) {

        with(itemView) {
            setOnClickListener { clickListener?.onClickContact(contact) }
            textView.text = contact.name[0].toString()
            contact_name.text = contact.name
        }
    }
}