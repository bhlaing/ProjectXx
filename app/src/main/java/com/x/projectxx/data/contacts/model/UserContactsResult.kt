package com.x.projectxx.data.contacts.model

sealed class UserContactsResult {
    class Success(val contacts: List<Contact>): UserContactsResult()
    class Fail(val error: String?): UserContactsResult()
}