package com.x.projectxx.domain.contact

import com.x.projectxx.data.contact.model.Contact
import com.x.projectxx.data.contact.model.ContactRelationshipType
import com.x.projectxx.domain.user.model.ContactStatus
import com.x.projectxx.domain.user.model.User
import java.io.InvalidObjectException

fun List<Contact>.toContactStatus() = mapContacts(this)

private fun mapContacts(contacts: List<Contact>) = contacts.map {
    User.Contact(
        it.userId ?: throw InvalidObjectException("Contact userid cannot be null!"),
        mapContactStatus(it.status)
    )
}

private fun mapContactStatus(relationshipType: ContactRelationshipType?) = relationshipType?.let {
    when (relationshipType) {
        ContactRelationshipType.CONFIRMED -> ContactStatus.CONFIRMED
        ContactRelationshipType.REQUEST -> ContactStatus.REQUEST
        ContactRelationshipType.PENDING -> ContactStatus.PENDING
    }
} ?: throw InvalidObjectException("Relationship status cannot be null!")
