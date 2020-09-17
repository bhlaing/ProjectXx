package com.x.projectxx.data.contact

import com.x.projectxx.data.contact.model.*
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    suspend fun setContact(request: SetContactRequest): Boolean
    suspend fun getUserContacts(userId: String): UserContactsResult
    suspend fun acceptContactRequest(userId: String): SimpleResult
    suspend fun deleteContact(request: DeleteContactRequest): SimpleResult
    suspend fun observeUserContacts(userId: String): Flow<List<Contact>>
}