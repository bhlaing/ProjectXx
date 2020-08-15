package com.x.projectxx.domain.chat.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.x.projectxx.data.ChatTranscript
import com.x.projectxx.data.message.MessageService
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CreateChatTranscript @Inject constructor(private val messageService: MessageService){
    operator fun invoke() : LiveData<ChatTranscript>{
        return liveData (Dispatchers.IO){

        }
    }

}