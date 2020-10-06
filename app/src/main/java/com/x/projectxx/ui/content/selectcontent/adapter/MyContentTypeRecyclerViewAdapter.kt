package com.x.projectxx.ui.content.selectcontent.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.x.contentlibrary.domain.ContentType
import com.x.projectxx.R
import com.x.projectxx.databinding.ItemContentTypeBinding

class MyContentTypeRecyclerViewAdapter: RecyclerView.Adapter<MyContentTypeRecyclerViewAdapter.ContentTypeViewHolder>() {

    private var contentTypes: List<ContentType> = listOf()

    fun updateContentTypes(contentTypes: List<ContentType>) {
        this.contentTypes = contentTypes
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentTypeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_content_type, parent, false)
        return ContentTypeViewHolder(view)
    }

    override fun onBindViewHolder(holderContentType: ContentTypeViewHolder, position: Int) =
        holderContentType.bind(contentTypes[position])


    override fun getItemCount(): Int = contentTypes.size

    inner class ContentTypeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val contentTypeBinding = ItemContentTypeBinding.bind(view)

        fun bind(contentType: ContentType) {
            contentTypeBinding.contentName.text = contentType.toString()
        }
    }
}