package br.felipe.parrot.activity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.felipe.parrot.R
import br.felipe.parrot.activity.view.listener.ContactClickListener
import br.felipe.parrot.activity.view.holder.ContactViewHolder
import br.felipe.parrot.activity.view.holder.ContactViewHolderBase
import br.felipe.parrot.activity.view.holder.ContactViewHolderHeader
import br.felipe.parrot.data.ui.Contact

class ContactsListAdapter(
    private val adapterLetter :AdapterLetter
) : RecyclerView.Adapter<ContactViewHolderBase>() {

    enum class AdapterLetter { FIRST, OTHER }
    enum class ViewType(val id: Int) { HEADER(0), OTHER(1) }

    private val items: MutableList<Contact> = mutableListOf()
    var contactClickListener: ContactClickListener? = null

    override fun getItemViewType(position: Int): Int {
        return when(adapterLetter) {
            AdapterLetter.FIRST -> if (position == 0) {
                ViewType.HEADER.id
            } else {
                ViewType.OTHER.id
            }
            AdapterLetter.OTHER -> ViewType.OTHER.id
        }
    }

    // Return items of the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolderBase {

        return when(viewType) {
            ViewType.HEADER.id -> ContactViewHolderHeader(
                LayoutInflater.from(parent.context).inflate(
                R.layout.main_view_holder_header, parent, false))
            else -> ContactViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.main_view_holder, parent, false)
            )
        }
    }

    // receive list of items, clean and add new ones
    fun refresh(newItems: List<Contact>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    // Return who many items in the list
    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ContactViewHolderBase, position: Int) {
        holder.bind(items[position], contactClickListener)
    }
}