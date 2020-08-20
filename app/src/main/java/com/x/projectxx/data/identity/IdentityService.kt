package com.x.projectxx.data.identity

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.x.projectxx.data.identity.model.UserProfile
import java.io.InvalidObjectException
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
        userCollection.document(userId)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result?.let {
                        cont.resume(parseSnapshotToUserProfile(it))
                    }
                        ?: cont.resumeWithException(InvalidObjectException("Error parsing UserProfileDocument to UserProfile"))

                } else {
                    cont.resumeWithException(
                        task.exception ?: Exception("Unknown exception occurred! ")
                    )
                }
            }.addOnFailureListener {
                cont.resumeWithException(it)
            }
    }

    override suspend fun getUserProfileByEmail(email: String): UserProfile? =
        suspendCoroutine { cont ->
            userCollection.whereEqualTo("email", email)
                .get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        task.result?.let {
                            if(it.documents.size > 0) {
                                cont.resume(parseSnapshotToUserProfile(it.documents.first()))
                            } else {
                                cont.resume(null)
                            }
                        }
                            ?: cont.resumeWithException(InvalidObjectException("Error parsing UserProfileDocument to UserProfile"))

                    } else {
                        cont.resumeWithException(
                            task.exception ?: Exception("Unknown exception occurred! ")
                        )
                    }
                }.addOnFailureListener {
                    cont.resumeWithException(it)
                }
        }

    private fun parseSnapshotToUserProfile(documentSnapshot: DocumentSnapshot) =
        documentSnapshot.toObject(UserProfile::class.java)

    override suspend fun createUserProfile(userProfile: UserProfile) =
        suspendCoroutine<UserProfile> { cont ->
            userCollection.document(userProfile.userId!!)
                .set(userProfile)
                .addOnSuccessListener {
                    cont.resume(userProfile)
                }.addOnFailureListener {
                    cont.resumeWithException(it)
                }
        }
}