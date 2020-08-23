package com.x.projectxx.data.contacts

import com.x.projectxx.data.contacts.model.SetContactRequest

interface ContactRepository {
    suspend fun setContact(request: SetContactRequest): Boolean
}