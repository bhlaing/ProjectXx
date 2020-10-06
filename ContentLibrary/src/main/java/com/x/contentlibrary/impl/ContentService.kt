package com.x.contentlibrary.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.x.contentlibrary.impl.model.ContentDO
import com.x.contentlibrary.ContentRepository
import com.x.contentlibrary.domain.ContentType
import com.x.contentlibrary.domain.UserContent
import com.x.contentlibrary.domain.mapper.toUserContent
import com.x.contentlibrary.impl.model.ContentValueDO
import com.x.contentlibrary.request.TextContentRequest
import com.x.firebasecore.domain.result.SimpleResult
import com.x.firebasecore.extensions.observe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.util.*

internal class ContentService constructor(cloudFirestoreDb: FirebaseFirestore): ContentRepository {
    companion object {
        private const val COLLECTION_USERS = "users"
        private const val COLLECTION_CONTENTS = "contents"
        private const val COLLECTION_CONTENT_VALUES = "content_values"
        private const val COLLECTION_TEXT_CONTENT = "text_content"
    }
    private val userCollection = cloudFirestoreDb.collection(COLLECTION_USERS)
    private val contentCollection = cloudFirestoreDb.collection(COLLECTION_CONTENT_VALUES)

    override suspend fun saveTextContent(userId: String, request: TextContentRequest) =
        try {
            val docRef = addTextContent(userId, request.text).await()
            getUserContentCollectionRef(userId)
                .document()
                .set(buildUserTextContent(docRef.id, request))
            SimpleResult.Success
        } catch (e: FirebaseFirestoreException) {
            SimpleResult.Fail(e)
        }

    @ExperimentalCoroutinesApi
    override fun observeUserContents(userId: String): Flow<List<UserContent>> =
        getUserContentCollectionRef(userId).observe { querySnapshot ->
            querySnapshot.documents.mapNotNull { documentSnapshot ->
                documentSnapshot.toObject(ContentDO::class.java)?.toUserContent(documentSnapshot.id)
            }
        }

    override fun getEligibleContentTypes(): List<ContentType> = ContentType.values().toList()

    private fun buildUserTextContent(docId: String, request: TextContentRequest) =
        ContentDO(
            docId,
            request.name,
            request.description,
            Date(),
            request.securityLevel,
            ContentType.TEXT
        )

    private fun addTextContent(userId: String, text: String) =
        getUserTextContentValuesCollectionRef(userId)
            .add(ContentValueDO(text))

    private fun getUserContentCollectionRef(userId: String) =
        userCollection.document(userId).collection(COLLECTION_CONTENTS)

    private fun getUserTextContentValuesCollectionRef(userId: String) =
        contentCollection.document(userId).collection(COLLECTION_TEXT_CONTENT)

}