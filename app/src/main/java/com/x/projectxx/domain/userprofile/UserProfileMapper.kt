package com.x.projectxx.domain.userprofile

import com.google.firebase.auth.FirebaseUser
import com.x.projectxx.data.identity.userprofile.UserProfile
import com.x.projectxx.domain.userprofile.model.User
import java.io.InvalidObjectException

// When app initialising card is done, this should be removed
// Domain user should never know anything about FirebaseAuthUser
fun FirebaseUser.toUserProfile() =
    mapFireBaseUserToUserProfile(this)

private fun mapFireBaseUserToUserProfile(firebaseUser: FirebaseUser) =
    firebaseUser.let {
        UserProfile(
            it.uid,
            it.displayName ?: "",
            it.photoUrl?.toString()
            , it.email
        )
    }

fun UserProfile.toUser() =
    mapUserProfileToUser(this)

private fun mapUserProfileToUser(userProfile: UserProfile) =
    userProfile.let {
        User(it.userId ?: throw InvalidObjectException("User id cannot be null!"), // userId should never be null
        it.displayName,
        it.image,
        it.email,
        it.status)
    }