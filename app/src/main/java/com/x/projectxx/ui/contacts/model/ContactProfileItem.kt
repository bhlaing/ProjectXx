package com.x.projectxx.ui.contacts.model


sealed class ContactProfileItem(
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
    ) : ContactProfileItem(userId, displayName, image, email, status)

    class PendingContact(
        userId: String,
        displayName: String?,
        image: String?,
        email: String?,
        status: String = "Life is good!"
    ) : ContactProfileItem(userId, displayName, image, email, status)


    class RequestConfirmContact(
        userId: String,
        displayName: String?,
        image: String?,
        email: String?,
        status: String = "Life is good!"
    ) : ContactProfileItem(userId, displayName, image, email, status)


    class ConfirmedContact(
        userId: String,
        displayName: String?,
        image: String?,
        email: String?,
        status: String = "Life is good!"
    ) : ContactProfileItem(userId, displayName, image, email, status)
}

