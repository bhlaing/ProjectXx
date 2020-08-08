package com.x.projectxx.ui.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.x.projectxx.R
import com.x.projectxx.data.ChatTranscript
import kotlinx.android.synthetic.main.item_chat_message.view.*

class ChatMessagesAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var messages: List<ChatTranscript.Message> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ChatMessageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_message, parent, false)
        )
    }

    fun updateMessages(messageList: List<ChatTranscript.Message>) {
        messages = messageList
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChatMessageViewHolder -> {
                holder.bind(messages[position])
            }
        }
    }

    internal class ChatMessageViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val messageText = itemView.messageTextView

        fun bind(message: ChatTranscript.Message) {
            messageText.text = message.text
        }
    }
}