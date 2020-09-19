package com.x.projectxx.domain.contact

import com.x.projectxx.data.contact.ContactService
import com.x.projectxx.data.contact.model.Contact
import com.x.projectxx.data.contact.model.ContactRelationshipType
import com.x.projectxx.data.contact.model.SetContactRequest
import com.x.projectxx.domain.shared.ResultInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class RequestContact @Inject constructor(private val contactService: ContactService) :
    ResultInteractor<RequestContact.Param, Boolean>() {

    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    class Param(
        val requesterId: String,
        val contactId: String
    )

    override suspend fun doWork(params: Param) =
        contactService.setContact(
            SetContactRequest(
                params.requesterId,
                Contact(params.contactId, ContactRelationshipType.PENDING)
            )
        )
}