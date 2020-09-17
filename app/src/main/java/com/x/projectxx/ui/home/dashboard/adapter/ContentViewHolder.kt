package com.x.projectxx.ui.home.dashboard.adapter

import com.x.projectxx.ui.content.model.UserContentItem
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.x.projectxx.databinding.ItemContentBinding

//inner class ContactItemViewHolder(
//    view: View
//) : RecyclerView.ViewHolder(view) {
//    private val binding = ItemContactDetailBinding.bind(view)
//
//    fun bind(contact: ContactDetails) {
//        binding.contactNameText.text = contact.displayName
//        binding.contactStatusText.setTextOrGone(contact.status)
//
//        contact.profileImage?.run {
//            Picasso.get().load(this.toAndroidUri()).into(binding.profileImage)
//        }
//    }
//}

abstract class ContentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(content: UserContentItem)
}

class DefaultSecurityContentItemHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val contentBinding = ItemContentBinding.bind(view)

     fun bind(content: UserContentItem) {
        with(contentBinding) {
            this.contentName.text = content.name
            this.descriptionText.text = content.description
            this.securityLevel.setCompoundDrawablesWithIntrinsicBounds(0, content.icon, 0, 0)
            this.editDate.text = content.createdDate
        }
    }
}
