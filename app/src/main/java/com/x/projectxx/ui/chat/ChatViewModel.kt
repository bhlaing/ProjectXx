package com.x.projectxx.ui.chat

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.x.projectxx.ui.chat.usecase.GetChatTranscript

class ChatViewModel @ViewModelInject constructor(getChatTranscript: GetChatTranscript) :
    ViewModel() {
    val messages = getChatTranscript.invoke(123)
}