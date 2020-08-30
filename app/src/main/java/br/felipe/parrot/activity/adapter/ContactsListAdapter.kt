package br.felipe.parrot.activity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.felipe.parrot.R
import br.felipe.parrot.activity.Contact
import br.felipe.parrot.activity.view.holder.ContactViewHolder

class ContactsListAdapter : RecyclerView.Adapter<ContactViewHolder>() {

    private val items: MutableList<Contact> = mutableListOf()

    // receive list of items, clean and add new ones
    fun refresh(newItems: List<Contact>) {
        items.clear()
        items.addAll(newItems)
    }

    // Return who many items in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Return items of the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_view_holder, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(items[position])
    }
}