package com.x.projectxx.data.identity

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException

import com.x.projectxx.data.identity.model.UserProfile
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class IdentityService @Inject constructor(cloudFirestoreDb: FirebaseFirestore) :
    IdentityRepository {
    companion object {
        private const val COLLECTION_USERS = "users"
    }

    private val userCollection = cloudFirestoreDb.collection(COLLECTION_USERS)

    override suspend fun getUserProfile(userId: String): UserProfile? {
        return try {
            val document = userCollection.document(userId).get().await()

            if (document.exists()) {
                return document.toObject(UserProfile::class.java)
            }
            null
        } catch (e: FirebaseFirestoreException) {
            null
        }

    }

    override suspend fun getUserProfileByEmail(email: String): UserProfile? {
        return try {
            val querySnapshot = userCollection.whereEqualTo("email", email)
                .get()
                .await()

            if (querySnapshot.isEmpty) {
                null
            } else {
                parseSnapshotToUserProfile(querySnapshot.documents.first())
            }
        } catch (firestoreException: FirebaseFirestoreException) {
            null
        }
    }

    private fun parseSnapshotToUserProfile(documentSnapshot: DocumentSnapshot) =
        documentSnapshot.toObject(UserProfile::class.java)
}