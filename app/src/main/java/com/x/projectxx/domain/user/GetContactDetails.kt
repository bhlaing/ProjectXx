package com.x.projectxx.domain.user

import androidx.annotation.StringRes
import com.x.projectxx.R
import com.x.projectxx.data.identity.IdentityRepository
import com.x.projectxx.domain.user.model.ContactDetails
import com.x.projectxx.domain.shared.RetrieveResultInteractor
import com.x.projectxx.domain.user.mappers.toContactDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetContactDetails @Inject constructor(private val identityRepository: IdentityRepository):
    RetrieveResultInteractor<GetContactDetails.Param, GetContactDetails.ContactDetailsResult>() {

    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun doWork(params: Param) = identityRepository.getUserProfile(params.userId)?.let {
             ContactDetailsResult.Success(it.toContactDetails())
         }?: ContactDetailsResult.Error(R.string.generic_error_message)

    data class Param(val userId: String)

    sealed class ContactDetailsResult {
        class Success(val contactDetails: ContactDetails) : ContactDetailsResult()
        class Error(@StringRes val error: Int) : ContactDetailsResult()
    }
}