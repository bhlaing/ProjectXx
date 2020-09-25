package com.x.projectxx.data.content

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.x.firebasecore.extensions.observe
import com.x.projectxx.data.content.model.AddContentRequest
import com.x.projectxx.data.content.model.Content
import com.x.projectxx.data.content.model.ContentWrapperDO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContentService @Inject constructor(cloudFirestoreDb: FirebaseFirestore) : ContentRepository {

    companion object {
        private const val COLLECTION_USERS = "users"
        private const val COLLECTION_CONTENTS = "contents"
    }

    private val userCollection = cloudFirestoreDb.collection(COLLECTION_USERS)

    @ExperimentalCoroutinesApi
    override fun observeUserContents(userId: String): Flow<List<ContentWrapperDO>> =
        getUserContentCollectionRef(userId).observe { querySnapshot ->
            querySnapshot.documents.mapNotNull { documentSnapshot ->
                documentSnapshot.toObject(Content::class.java)?.let { content ->
                    ContentWrapperDO(documentSnapshot.id, content)
                }
            }
        }

    override fun addUserContent(request: AddContentRequest) =
        try {
            getUserContentCollectionRef(request.userId)
                .document()
                .set(request.content)
            true
        } catch (e: FirebaseFirestoreException) {
            false
        }


    private fun getUserContentCollectionRef(userId: String) =
        userCollection.document(userId).collection(COLLECTION_CONTENTS)
}