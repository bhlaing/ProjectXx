package com.x.projectxx.data.contacts

import com.x.projectxx.data.contacts.model.DeleteContactRequest
import com.x.projectxx.data.contacts.model.SimpleResult
import com.x.projectxx.data.contacts.model.SetContactRequest
import com.x.projectxx.data.contacts.model.UserContactsResult

interface ContactRepository {
    suspend fun setContact(request: SetContactRequest): Boolean
    suspend fun getUserContacts(userId: String): UserContactsResult
    suspend fun acceptContactRequest(userId: String): SimpleResult
    suspend fun deleteContact(request: DeleteContactRequest): SimpleResult
}