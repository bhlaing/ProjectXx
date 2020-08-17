package com.x.projectxx.data.identity.userprofile

data class UserProfile(
    val userId: String? = null,
    val displayName: String = "",
    val image: String? = null,
    val email: String? = null,
    val status: String = "Life is good!")