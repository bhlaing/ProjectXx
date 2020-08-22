package com.x.projectxx.data.identity

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.x.projectxx.data.identity.model.UserProfile
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

    override suspend fun getUserProfile(userId: String): UserProfile? = suspendCoroutine { cont ->
        userCollection.document("22")
            .get().addOnSuccessListener {
                if(it.exists()) {
                    cont.resume(parseSnapshotToUserProfile(it))
                } else {
                    cont.resume(null)
                }
            }.addOnFailureListener {
                cont.resumeWithException(it)
            }
    }

    override suspend fun getUserProfileByEmail(email: String): UserProfile? =
        suspendCoroutine { cont ->
            userCollection.whereEqualTo("email", email)
                .get().addOnSuccessListener {
                    if(!it.isEmpty) {
                        cont.resume(parseSnapshotToUserProfile(it.documents.first()))
                    } else {
                        cont.resume(null)
                    }
                }.addOnFailureListener {
                    cont.resumeWithException(it)
                }
        }

    private fun parseSnapshotToUserProfile(documentSnapshot: DocumentSnapshot) =
        documentSnapshot.toObject(UserProfile::class.java)
}