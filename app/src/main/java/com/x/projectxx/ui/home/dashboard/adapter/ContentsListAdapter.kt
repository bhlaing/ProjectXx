package com.x.projectxx.ui.home.dashboard.adapter

import com.x.projectxx.ui.content.model.UserContentItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.x.projectxx.R
import com.x.projectxx.application.extensions.setTextOrGone
import com.x.projectxx.databinding.ItemContentBinding

class ContentsListAdapter :
    RecyclerView.Adapter<ContentsListAdapter.ContentItemHolder>() {

    private var contents: List<UserContentItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentItemHolder {
        return ContentItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_content, parent, false)
        )
    }

    fun updateContents(contents: List<UserContentItem>) {
        this.contents = contents
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = contents.size

    override fun onBindViewHolder(holder: ContentItemHolder, position: Int) =
        holder.bind(contents[position])


    inner class ContentItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val contentBinding = ItemContentBinding.bind(view)

        fun bind(content: UserContentItem) {
            with(contentBinding) {
//                this.contentName.setCompoundDrawablesWithIntrinsicBounds(content.icon, 0, 0, 0)

                this.contentName.text = content.name
                this.descriptionText.setTextOrGone(content.description)
//                this.securityLevel.setCompoundDrawablesWithIntrinsicBounds(0, content.icon, 0, 0)
                this.securityLevel.text = "Security Level: " + content.securityLevel.name
                this.editDate.text = "Created on: " + content.createdDate
            }
        }
    }
}