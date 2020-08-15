package com.x.projectxx.data.message

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.x.projectxx.data.ChatTranscript
import java.io.InvalidObjectException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MessageService @Inject constructor(private val cloudFireStoreDb: FirebaseFirestore) {
    companion object {
        private const val COLLECTION_MESSAGES = "messages"
    }

    suspend fun createMessages(userId:String,messages: List<ChatTranscript.Message>) =
        suspendCoroutine<List<ChatTranscript.Message>> { cont ->
            cloudFireStoreDb?.collection(COLLECTION_MESSAGES)
                .document(userId)
                .set(messages).addOnSuccessListener {
                    cont.resume(messages)
                }.addOnFailureListener {
                    cont.resumeWithException(it)
                }
        }

    suspend fun getMessages() = suspendCoroutine<ChatTranscript?> { cont ->
        cloudFireStoreDb.collection(COLLECTION_MESSAGES)
            .document()
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result?.let { documentSnapshot ->
                        cont.resume(parseSnapShotToMessages(documentSnapshot))
                    }
                        ?: cont.resumeWithException(InvalidObjectException("Unable to parse the ChatTranscript Object"))
                }
            }.addOnFailureListener {
                cont.resumeWithException(it)
            }
    }

    private fun parseSnapShotToMessages(documentSnapshot: DocumentSnapshot) =
        documentSnapshot.toObject(ChatTranscript::class.java)

}