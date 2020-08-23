package com.x.projectxx.data.contacts.model

class SetContactRequest(
    val requesterId: String,
    val contact: Contact
) {
    class Contact(
        val userId: String,
        val status: ContactRelationshipType
    )
}
