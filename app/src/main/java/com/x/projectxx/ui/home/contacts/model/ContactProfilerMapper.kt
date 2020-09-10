package com.x.projectxx.ui.home.contacts.model

import com.x.projectxx.domain.user.model.ContactStatus
import com.x.projectxx.domain.user.model.User

fun User.toSearchUserProfileItem(contactStatus: ContactStatus?) =
    mapToSearchUserProfileItem(contactStatus, this)

private fun mapToSearchUserProfileItem(contactStatus: ContactStatus?, user: User) = contactStatus?.let {
    when (it) {
        ContactStatus.PENDING -> mapAsPendingContact(user)
        ContactStatus.CONFIRMED -> mapAsConfirmedContact(user)
        ContactStatus.REQUEST -> mapAsRequestConfirm(user)
    }
} ?: mapAsUnknownContact(user)


private fun mapAsPendingContact(user: User) = user.let {
    SearchProfileItem.Pending(
        it.userId,
        it.displayName,
        it.image,
        it.email,
        it.status
    )
}

private fun mapAsRequestConfirm(user: User) = user.let {
    SearchProfileItem.RequestConfirm(
        it.userId,
        it.displayName,
        it.image,
        it.email,
        it.status
    )
}

private fun mapAsConfirmedContact(user: User) = user.let {
    SearchProfileItem.Confirmed(
        it.userId,
        it.displayName,
        it.image,
        it.email,
        it.status
    )
}

private fun mapAsUnknownContact(user: User) = user.let {
    SearchProfileItem.Unknown(
        it.userId,
        it.displayName,
        it.image,
        it.email,
        it.status
    )
}
