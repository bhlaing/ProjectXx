package com.x.projectxx.data.chat

import java.util.Date

data class ChatTranscript(val messages: List<Message>) {

    // Set up for different type of messages to come
    sealed class Message(val text: String, val timeStamp: Date, val fromUser: Sender, val image: String) {
        class Text(text: String, timeStamp: Date, fromUser: Sender, image: String): Message(text, timeStamp, fromUser, image)
    }

    sealed class Sender {
        object Myself: Sender()
        class Other(val name: String) : Sender()// we should add ID
    }
}