package com.x.projectxx.domain.contact

import com.x.projectxx.R
import com.x.projectxx.data.identity.IdentityRepository
import com.x.projectxx.domain.contact.RetrieveUserContacts.*
import com.x.projectxx.domain.shared.ResultInteractor
import com.x.projectxx.domain.contact.model.ContactDetails
import com.x.projectxx.domain.user.mappers.toContactDetails
import com.x.projectxx.domain.user.model.ContactStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class RetrieveContactList @Inject constructor(
    private val retrieveUserContacts: RetrieveUserContacts,
    private val identityRepository: IdentityRepository
) : ResultInteractor<RetrieveContactList.Param, RetrieveContactList.Result>() {

    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun doWork(params: Param): Result {
        return when(val result = getContacts(params.userId)) {
            is RetrieveContactResult.Success ->
                Result.Success(result.contacts.mapNotNull { retrieveContactDetails(it.userId, it.status) })
            is RetrieveContactResult.Error ->
                Result.Fail(R.string.contact_generic_error)
        }
    }

    private suspend fun retrieveContactDetails(userId: String, status: ContactStatus) =
        identityRepository.getUserProfile(userId)?.toContactDetails(status)

    private suspend fun getContacts(userId: String) =
        RetrieveUserContacts.Param(userId).run { retrieveUserContacts(this) }


    class Param(val userId: String)

    sealed class Result {
        class Success(val contacts: List<ContactDetails>): Result()
        class Fail(val error: Int): Result()
    }

}