package com.x.projectxx.domain.user.mappers

import com.x.projectxx.data.identity.model.UserProfile
import com.x.projectxx.domain.contacts.model.ContactDetails
import java.io.InvalidObjectException

fun UserProfile.toContactDetails() =
    mapUserProfileToContactDetails(this)

private fun mapUserProfileToContactDetails(userProfile: UserProfile) =
    userProfile.let {
        ContactDetails(it.userId ?: throw InvalidObjectException("User id cannot be null!"), // userId should never be null
            it.displayName,
            it.image)
    }