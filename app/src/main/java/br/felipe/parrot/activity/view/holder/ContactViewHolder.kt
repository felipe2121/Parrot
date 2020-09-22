package br.felipe.parrot.activity.view.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.felipe.parrot.activity.Contact
import br.felipe.parrot.activity.listener.ContactClickListener
import kotlinx.android.synthetic.main.main_view_holder.view.*

class ContactViewHolder(view: View): RecyclerView.ViewHolder(view) {

    // layout items
    fun bind(
        contact: Contact,
        contactClickListener: ContactClickListener?
    ) {
        with(itemView) {
            contact_name.text = contact.name
        }
    }
}