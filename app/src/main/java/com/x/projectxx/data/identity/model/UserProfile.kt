package com.x.projectxx.data.identity.model

data class UserProfile(
    val userId: String? = null,
    val displayName: String = "",
    val image: String? = null,
    val email: String? = null,
    val status: String = "Life is good!",
    val contacts: List<Contact> = listOf(Contact(userId = "m8aHtBZvdzXYCqkdtmRTHxcsYEH3"))) {

    data class Contact(val userId: String? = null)
}
