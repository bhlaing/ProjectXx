package com.x.projectxx.domain.contact

import androidx.annotation.StringRes
import com.x.projectxx.R
import com.x.projectxx.data.contacts.ContactRepository
import com.x.projectxx.data.contacts.model.UserContactsResult
import com.x.projectxx.domain.shared.RetrieveResultInteractor
import com.x.projectxx.domain.user.mappers.toUser
import com.x.projectxx.domain.user.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class RetrieveUserContacts @Inject constructor(private val contactRepository: ContactRepository) :
    RetrieveResultInteractor<RetrieveUserContacts.Param, RetrieveUserContacts.RetrieveContactResult>() {

    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun doWork(params: Param) =
        contactRepository.getUserContacts(params.userId).let {
            when (it) {
                is UserContactsResult.Success -> RetrieveContactResult.Success(it.contacts.toContactStatus())
                is UserContactsResult.Fail -> RetrieveContactResult.Error(R.string.generic_error_message)
            }
        }

    class Param(val userId: String)

    sealed class RetrieveContactResult {
        class Success(val contacts: List<User.Contact>) : RetrieveContactResult()
        class Error(@StringRes val error: Int) : RetrieveContactResult()
    }
}