package com.x.projectxx.data.identity.model

import com.x.projectxx.data.contact.model.Contact

data class UserProfile(
    val userId: String? = null,
    val displayName: String = "",
    val image: String? = null,
    val email: String? = null,
    val status: String = "Life is good!",
    var contacts: List<Contact> = emptyList()) {
}
