package com.x.projectxx.application.authentication.userprofile.mapper

import com.google.firebase.auth.FirebaseUser
import com.x.projectxx.application.authentication.userprofile.UserProfile

fun FirebaseUser.toUserProfile() = mapFireBaseUserToUserProfile(this)

private fun mapFireBaseUserToUserProfile (firebaseUser: FirebaseUser) =
    firebaseUser.let { UserProfile( it.uid, it.displayName ?: "", it.photoUrl) }