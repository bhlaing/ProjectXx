package com.x.projectxx.ui.home.contacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.x.projectxx.R
import com.x.projectxx.application.extensions.setTextOrGone
import com.x.projectxx.application.extensions.toAndroidUri
import com.x.projectxx.databinding.ItemContactDetailBinding
import com.x.projectxx.domain.contact.model.ContactDetails

class ContactListAdapter :
    RecyclerView.Adapter<ContactListAdapter.ContactItemViewHolder>() {

    private var contacts: List<ContactDetails> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactItemViewHolder {
        return ContactItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_contact_detail, parent, false)
        )
    }

    fun updateContacts(messageList: List<ContactDetails>) {
        contacts = messageList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactItemViewHolder, position: Int) =
        holder.bind(contacts[position])

    inner class ContactItemViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        private val binding = ItemContactDetailBinding.bind(view)

        fun bind(contact: ContactDetails) {
            binding.contactNameText.text = contact.displayName
            binding.contactStatusText.setTextOrGone(contact.status)

            contact.profileImage?.run {
                Picasso.get().load(this.toAndroidUri()).into(binding.profileImage)
            }
        }
    }
}