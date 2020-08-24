package com.x.projectxx.ui.contacts.model

import com.x.projectxx.R

sealed class SearchUserProfileItem(
    val userId: String,
    val displayName: String?,
    val image: String?,
    val email: String?,
    val status: String = "Life is good!"
) {
    class UnknownContact(
        userId: String,
        displayName: String?,
        image: String?,
        email: String?,
        status: String = "Life is good!"
    ) : SearchUserProfileItem(userId, displayName, image, email, status)

    class PendingContact(
        userId: String,
        displayName: String?,
        image: String?,
        email: String?,
        status: String = "Life is good!",
        val icon: Int = R.drawable.ic_pending_24): SearchUserProfileItem(userId, displayName, image, email, status)


    class RequestPending(
        userId: String,
        displayName: String?,
        image: String?,
        email: String?,
        status: String = "Life is good!",
        val icon: Int = R.drawable.ic_reply_24): SearchUserProfileItem(userId, displayName, image, email, status)
}

