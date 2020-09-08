package com.x.projectxx.domain.contact

import com.x.projectxx.domain.shared.ResultInteractor
import com.x.projectxx.domain.user.GetContactDetails
import com.x.projectxx.domain.contact.model.ContactDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class RetrieveContactList @Inject constructor(
    private val getContactDetails: GetContactDetails,
    private val retrieveUserContacts: RetrieveUserContacts
) : ResultInteractor<RetrieveContactList.Param, RetrieveContactList.Result>() {

    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun doWork(params: Param): Result {


        when(val result = _retrieveUserContacts(params.userId)) {
//            res
        }


        TODO("Not yet implemented")
    }

    private suspend fun populateDetails(contacts: List<ContactDetails>) {

    }

    private suspend fun _retrieveUserContacts(userId: String) =
        RetrieveUserContacts.Param(userId).run { retrieveUserContacts(this) }

    class Param(val userId: String)

    sealed class Result {
        class Success(val contacts: List<ContactDetails>)
        class Fail(val error: Int)
    }

}