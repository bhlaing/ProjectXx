package com.x.projectxx.data.contacts

import com.google.firebase.firestore.FirebaseFirestore
import com.x.projectxx.data.contacts.model.Contact
import com.x.projectxx.data.contacts.model.SetContactRequest
import com.x.projectxx.data.contacts.model.UserContactsResult
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ContactService @Inject constructor(cloudFirestoreDb: FirebaseFirestore) : ContactRepository {
    companion object {
        private const val COLLECTION_USER = "users"
        private const val COLLECTION_CONTACTS = "contacts"
    }

    private val userCollection = cloudFirestoreDb.collection(COLLECTION_USER)

    override suspend fun setContact(request: SetContactRequest): Boolean =
        suspendCoroutine { cont ->
            getContactCollection(request.requesterId)
                .document(request.contact.userId!!)
                .set(request.contact)
                .addOnSuccessListener {
                    cont.resume(true)
                }.addOnFailureListener {
                    cont.resume(false)
                }
        }

    override suspend fun getUserContacts(userId: String): UserContactsResult =
        suspendCoroutine { cont ->
            getContactCollection(userId)
                .get()
                .addOnSuccessListener { documents ->
                    val contacts = mutableListOf<Contact>()

                    documents.forEach {
                        contacts.add(it.toObject(Contact::class.java))
                    }

                    cont.resume(UserContactsResult.Success(contacts))
                }.addOnFailureListener {
                    cont.resume(UserContactsResult.Fail(it.message))
                }

        }


    private fun getContactCollection(userId: String) = userCollection.document(userId)
        .collection(COLLECTION_CONTACTS)
}