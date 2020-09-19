package com.x.projectxx.domain.contact

import com.x.projectxx.R
import com.x.projectxx.data.contact.ContactRepository
import com.x.projectxx.data.identity.IdentityRepository
import com.x.projectxx.domain.shared.ResultInteractor
import com.x.projectxx.domain.contact.model.ContactDetails
import com.x.projectxx.domain.exceptions.GenericException
import com.x.projectxx.domain.user.mappers.mapToContactStatus
import com.x.projectxx.domain.user.mappers.toContactDetails
import com.x.projectxx.domain.user.model.ContactStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ObserveContactList @Inject constructor(
    private val contactRepository: ContactRepository,
    private val identityRepository: IdentityRepository
) : ResultInteractor<ObserveContactList.Param, Flow<List<ContactDetails>>>() {

    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun doWork(params: Param): Flow<List<ContactDetails>> {
        return runCatching { observerContacts(params.userId) }
            .getOrElse {
                throw GenericException(R.string.generic_error_message)
            }
    }

    private suspend fun observerContacts(userId: String): Flow<List<ContactDetails>> =
        contactRepository.observeUserContacts(userId).map { contactList ->
            contactList.mapNotNull {
                retrieveContactDetails(
                    it.userId!!,
                    mapToContactStatus(it.status!!)
                )
            }
        }

    private suspend fun retrieveContactDetails(userId: String, status: ContactStatus) =
        identityRepository.getUserProfile(userId)?.toContactDetails(status)

    class Param(val userId: String)
}


