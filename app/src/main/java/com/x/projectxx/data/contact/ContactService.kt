package com.x.projectxx.data.contact

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.functions.FirebaseFunctions
import com.x.firebasecore.extensions.observe
import com.x.projectxx.data.contact.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ContactService @Inject constructor(
    cloudFirestoreDb: FirebaseFirestore,
    private val functions: FirebaseFunctions
) : ContactRepository {
    companion object {
        private const val COLLECTION_USER = "users"
        private const val COLLECTION_CONTACTS = "contacts"
        private const val FUNCTION_ACCEPT_CONTACT = "acceptContact"
    }

    private val userCollection = cloudFirestoreDb.collection(COLLECTION_USER)

    override suspend fun setContact(request: SetContactRequest): Boolean {
        return try {
            getContactCollection(request.requesterId)
                .document(request.contact.userId!!)
                .set(request.contact)
            true
        } catch (e: FirebaseFirestoreException) {
            false
        }
    }

    override suspend fun getUserContacts(userId: String): UserContactsResult {
        return try {
            val documents = getContactCollection(userId).get().await()
            val contacts = documents.map { it.toObject(Contact::class.java) }

            UserContactsResult.Success(contacts)
        } catch (e: FirebaseFirestoreException) {
            UserContactsResult.Fail(e.message)
        }
    }

    override suspend fun acceptContactRequest(userId: String): SimpleResult =
        suspendCoroutine { cont ->
            getAcceptContactFunction()
                .call(userId)
                .addOnSuccessListener {
                    cont.resume(SimpleResult.Success)
                }
                .addOnFailureListener {
                    cont.resume((SimpleResult.Fail(it)))
                }
        }

    override suspend fun deleteContact(request: DeleteContactRequest): SimpleResult {
        return try {
            getContactCollection(request.userId)
                .document(request.contactId)
                .delete()

            SimpleResult.Success
        } catch (exception: FirebaseFirestoreException) {
            SimpleResult.Fail(exception)
        }
    }

    @ExperimentalCoroutinesApi
    override suspend fun observeUserContacts(userId: String): Flow<List<Contact>> =
        getContactCollection(userId).observe { it.toObjects(Contact::class.java) }


    private fun getAcceptContactFunction() = functions.getHttpsCallable(FUNCTION_ACCEPT_CONTACT)

    private fun getContactCollection(userId: String) = userCollection.document(userId)
        .collection(COLLECTION_CONTACTS)
}

