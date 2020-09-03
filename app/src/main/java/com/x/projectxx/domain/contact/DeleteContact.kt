package com.x.projectxx.domain.contact

import com.x.projectxx.R
import com.x.projectxx.data.contacts.ContactRepository
import com.x.projectxx.data.contacts.model.DeleteContactRequest
import com.x.projectxx.data.contacts.model.SimpleResult
import com.x.projectxx.domain.shared.ResultInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DeleteContact @Inject constructor(private val contactRepository: ContactRepository) :
    ResultInteractor<DeleteContact.Param, DeleteContact.DeleteResult>() {

    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun doWork(params: Param): DeleteResult =
        contactRepository.deleteContact(DeleteContactRequest(params.userId, params.contactId)).let {
            when(it) {
                is SimpleResult.Success -> DeleteResult.Success
                is SimpleResult.Fail -> DeleteResult.Fail(R.string.generic_error_message)
            }
        }

    class Param(val userId: String, val contactId: String)

    sealed class DeleteResult {
        object Success : DeleteResult()
        class Fail(val error: Int?) : DeleteResult()
    }
}