package com.x.projectxx.ui.contacts.model

import com.x.projectxx.R

sealed class ContactProfileItem(
    val userId: String,
    val displayName: String?,
    val image: String?,
    val email: String?,
    val status: String = "Life is good!",
    val icon: Int
) {
    class UnknownContact(
        userId: String,
        displayName: String?,
        image: String?,
        email: String?,
        status: String = "Life is good!",
        icon: Int = R.drawable.ic_person_add_24
    ) : ContactProfileItem(userId, displayName, image, email, status, icon)

    class PendingContact(
        userId: String,
        displayName: String?,
        image: String?,
        email: String?,
        status: String = "Life is good!",
        icon: Int = R.drawable.ic_pending_24
    ) : ContactProfileItem(userId, displayName, image, email, status, icon)


    class RequestConfirmContact(
        userId: String,
        displayName: String?,
        image: String?,
        email: String?,
        status: String = "Life is good!",
        icon: Int = R.drawable.ic_reply_24
    ) : ContactProfileItem(userId, displayName, image, email, status, icon)


    class ConfirmedContact(
        userId: String,
        displayName: String?,
        image: String?,
        email: String?,
        status: String = "Life is good!",
        icon: Int = R.drawable.ic_reply_24
    ) : ContactProfileItem(userId, displayName, image, email, status, icon)
}

