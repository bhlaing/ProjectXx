package com.x.projectxx.ui.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.x.projectxx.R
import com.x.projectxx.data.ChatTranscript
import kotlinx.android.synthetic.main.item_chat_message.view.*

class ChatMessagesAdapter :
    RecyclerView.Adapter<ChatMessagesAdapter.ChatMessageViewHolder>() {

    private var messages: List<ChatTranscript.Message> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMessageViewHolder {
        return ChatMessageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_message, parent, false)
        )
    }

    fun updateMessages(messageList: List<ChatTranscript.Message>) {
        messages = messageList
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: ChatMessageViewHolder, position: Int) {
        when (holder) {
            is ChatMessageViewHolder -> {
                holder.bind(messages[position])
            }
        }
    }

    inner class ChatMessageViewHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {

        fun bind(message: ChatTranscript.Message) {
            view.myselfMessageTextView.text = message.text
        }
    }
}