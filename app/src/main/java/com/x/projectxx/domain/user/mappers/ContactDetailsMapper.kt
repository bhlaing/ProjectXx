package com.x.projectxx.domain.user.mappers

import com.x.projectxx.data.identity.model.UserProfile
import com.x.projectxx.domain.contact.model.ContactDetails
import com.x.projectxx.domain.user.model.ContactStatus
import java.io.InvalidObjectException

// User profile is does not have ContactStatus since its a stand alone profile
// Thus if we want to transform a user profile to contact, we need to find out the ContactStatus
fun UserProfile.toContactDetails(status: ContactStatus) =
    mapUserProfileToContactDetails(this, status)

private fun mapUserProfileToContactDetails(userProfile: UserProfile, status: ContactStatus) =
    userProfile.let {
        ContactDetails(
            it.userId
                ?: throw InvalidObjectException("User id cannot be null!"), // userId should never be null
            it.displayName,
            it.status,
            it.image,
            status
        )
    }