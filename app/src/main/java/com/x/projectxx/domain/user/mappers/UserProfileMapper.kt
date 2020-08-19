package com.x.projectxx.domain.user.mappers

import com.x.projectxx.data.identity.model.UserProfile
import com.x.projectxx.domain.user.model.User
import java.io.InvalidObjectException

fun UserProfile.toUser() =
    mapUserProfileToUser(this)

private fun mapUserProfileToUser(userProfile: UserProfile) =
    userProfile.let {
        User(it.userId
            ?: throw InvalidObjectException("User id cannot be null!"), // userId should never be null
            it.displayName,
            it.image,
            it.email,
            it.status,
            it.contacts.map { userProfileContact -> // map (convert) contacts from data object to domain object
                mapToUserContact(userProfileContact)
            }
        )
    }

private fun mapToUserContact(contact: UserProfile.Contact) =
    contact.let {
        User.Contact(it.userId ?: throw InvalidObjectException("Contact id cannot be null!"))
    }