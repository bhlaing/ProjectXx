package com.x.projectxx.feature.chat.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.x.projectxx.feature.chat.usecase.GetChatTranscript

class ChatViewModel @ViewModelInject constructor(usecase:GetChatTranscript): ViewModel(){
    val messages = usecase.invoke(123)
}