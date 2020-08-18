package com.x.projectxx.domain.chat.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.x.projectxx.data.ChatTranscript
import com.x.projectxx.data.ChatTranscript.Sender.Other
import com.x.projectxx.data.ChatTranscript.Message.Text
import kotlinx.coroutines.Dispatchers
import java.util.*
import javax.inject.Inject

class GetChatTranscript @Inject constructor() {
     operator fun invoke(userId :Int): LiveData<ChatTranscript> {
        return liveData (Dispatchers.IO) {
             emit(ChatTranscript(listOf(
                 buildChatTextMessage("Hi", Date("1/1/2000"), Other("Billy"), "someImageUrl"),
                 buildChatTextMessage("How are you?", Date("1/1/2000"), Other("Billy"), "someImageUrl")
             )))
        }
    }
}

fun buildChatTextMessage(text: String = "",
                         timeStamp: Date = Date("1/1/2000"),
                         sender: ChatTranscript.Sender = ChatTranscript.Sender.Myself,
                         image: String = "imageuri") = Text(text, timeStamp, sender, image)
