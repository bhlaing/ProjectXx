package com.x.projectxx.domain.contact.model

import com.x.projectxx.domain.user.model.ContactStatus

class ContactDetails(
    val userId: String,
    val displayName: String,
    val status: String?,
    val profileImage: String?,
    val contactStatus: ContactStatus
)