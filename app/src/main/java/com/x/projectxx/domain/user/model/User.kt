package com.x.projectxx.domain.user.model

data class User(val userId: String,
val displayName: String?,
val image: String?,
val email: String?,
val status: String = "Life is good!",
val contacts: List<Contact> = emptyList()) {
    data class Contact(val userId: String)
}