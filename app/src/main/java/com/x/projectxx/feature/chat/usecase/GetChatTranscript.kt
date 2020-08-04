package com.x.projectxx.feature.chat.usecase

import com.x.projectxx.data.ChatTranscript
import javax.inject.Inject

class GetChatTranscript @Inject constructor() {

     operator fun invoke(userId :Int): ChatTranscript {

        return ChatTranscript(listOf())
    }
}