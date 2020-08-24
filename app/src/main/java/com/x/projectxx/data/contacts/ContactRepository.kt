package com.x.projectxx.data.contacts

import com.x.projectxx.data.contacts.model.SetContactRequest
import com.x.projectxx.data.contacts.model.UserContactsResult

interface ContactRepository {
    suspend fun setContact(request: SetContactRequest): Boolean
    suspend fun getUserContacts(userId: String): UserContactsResult
}