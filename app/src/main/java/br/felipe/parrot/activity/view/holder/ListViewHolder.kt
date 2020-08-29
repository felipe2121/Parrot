package br.felipe.parrot.activity.view.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.felipe.parrot.activity.Contact
import kotlinx.android.synthetic.main.main_view_holder.view.*

class ListViewHolder(view: View): RecyclerView.ViewHolder(view) {

    // layout items
    fun bind(contact: Contact) {
        with(itemView) {
            contact_name.text = contact.name
            contact_phone.text = contact.phone
        }
    }
}