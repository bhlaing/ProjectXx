package com.x.projectxx.ui.home.contacts.model


sealed class SearchProfileItem(
    val userId: String,
    val displayName: String?,
    val image: String?,
    val email: String?,
    val status: String = "Life is good!"
) {
    class Unknown(
        userId: String,
        displayName: String?,
        image: String?,
        email: String?,
        status: String = "Life is good!"
    ) : SearchProfileItem(userId, displayName, image, email, status)

    class Pending(
        userId: String,
        displayName: String?,
        image: String?,
        email: String?,
        status: String = "Life is good!"
    ) : SearchProfileItem(userId, displayName, image, email, status)


    class RequestConfirm(
        userId: String,
        displayName: String?,
        image: String?,
        email: String?,
        status: String = "Life is good!"
    ) : SearchProfileItem(userId, displayName, image, email, status)


    class Confirmed(
        userId: String,
        displayName: String?,
        image: String?,
        email: String?,
        status: String = "Life is good!"
    ) : SearchProfileItem(userId, displayName, image, email, status)
}

