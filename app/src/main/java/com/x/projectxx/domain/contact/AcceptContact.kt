package com.x.projectxx.domain.contact

import com.x.projectxx.data.contacts.ContactRepository
import com.x.projectxx.data.contacts.model.AcceptContactResult
import com.x.projectxx.domain.shared.RetrieveResultInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class AcceptContact @Inject constructor(private val contactRepository: ContactRepository) :
    RetrieveResultInteractor<AcceptContact.Param, AcceptContact.AcceptResult>() {

    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun doWork(params: Param): AcceptResult =
        contactRepository.acceptContactRequest(params.userId).let {
            when (it) {
                is AcceptContactResult.Success -> AcceptResult.Success
                is AcceptContactResult.Fail -> AcceptResult.Fail(it.exception.message)
            }
        }

    class Param(val userId: String)

    sealed class AcceptResult {
        object Success : AcceptResult()
        class Fail(val error: String?) : AcceptResult()
    }
}