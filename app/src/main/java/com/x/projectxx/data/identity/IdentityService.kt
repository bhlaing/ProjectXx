package com.x.projectxx.data.identity

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.x.projectxx.data.identity.userprofile.UserProfile
import com.x.projectxx.data.identity.userprofile.mapper.toUserProfile
import java.io.InvalidObjectException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class IdentityService @Inject constructor(private val cloudFirestoreDb: FirebaseFirestore) {
     companion object {
        private const val COLLECTION_USERS = "users"
    }

    suspend fun getUserProfile(userId: String) = suspendCoroutine<UserProfile?> { cont ->
        cloudFirestoreDb.collection(COLLECTION_USERS)
            .document(userId)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                        task.result?.let {
                            cont.resume(parseSnapshotToUserProfile(it))
                        } ?: cont.resumeWithException(InvalidObjectException("Error parsing UserProfileDocument to UserProfile"))

                } else {
                    cont.resumeWithException(
                        task.exception ?: Exception("Unknown exception occured! ")
                    )
                }
            }.addOnFailureListener {
                cont.resumeWithException(it)
            }
    }

    private fun parseSnapshotToUserProfile(documentSnapshot: DocumentSnapshot) =
    documentSnapshot.toObject(UserProfile::class.java)

    suspend fun createUserProfile(firebaseUser: FirebaseUser) = suspendCoroutine<UserProfile> { cont ->
            val userProfile = firebaseUser.toUserProfile()

            cloudFirestoreDb.collection(COLLECTION_USERS)
                .document(userProfile.userId!!)
                .set(userProfile)
                .addOnSuccessListener {
                    cont.resume(userProfile)
                }.addOnFailureListener {
                    cont.resumeWithException(it)
                }
        }
}