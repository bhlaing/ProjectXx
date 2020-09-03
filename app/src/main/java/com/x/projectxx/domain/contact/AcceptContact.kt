package com.x.projectxx.domain.contact

import com.x.projectxx.R
import com.x.projectxx.data.contacts.ContactRepository
import com.x.projectxx.data.contacts.model.SimpleResult
import com.x.projectxx.domain.shared.ResultInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class AcceptContact @Inject constructor(private val contactRepository: ContactRepository) :
    ResultInteractor<AcceptContact.Param, AcceptContact.AcceptResult>() {

    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun doWork(params: Param): AcceptResult =
        contactRepository.acceptContactRequest(params.userId).let {
            when (it) {
                is SimpleResult.Success -> AcceptResult.Success
                is SimpleResult.Fail -> AcceptResult.Fail(R.string.generic_error_message)
            }
        }

    class Param(val userId: String)

    sealed class AcceptResult {
        object Success : AcceptResult()
        class Fail(val error: Int?) : AcceptResult()
    }
}