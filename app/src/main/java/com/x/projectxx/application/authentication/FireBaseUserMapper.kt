package com.x.projectxx.application.authentication

import com.google.firebase.auth.FirebaseUser
import com.x.projectxx.data.identity.model.UserProfile

fun FirebaseUser.toUserProfile() =
    mapFireBaseUserToUserProfile(this)

private fun mapFireBaseUserToUserProfile(firebaseUser: FirebaseUser) =
    firebaseUser.let {
        UserProfile(
            it.uid,
            it.displayName ?: "",
            it.photoUrl?.toString(),
            it.email
        )
    }