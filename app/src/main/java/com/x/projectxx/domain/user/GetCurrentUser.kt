package com.x.projectxx.domain.user

import androidx.annotation.StringRes
import com.x.projectxx.R
import com.x.projectxx.application.authentication.LoginManager
import com.x.projectxx.data.identity.IdentityRepository
import com.x.projectxx.domain.shared.ResultInteractor
import com.x.projectxx.domain.user.mappers.toUser
import com.x.projectxx.domain.user.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetCurrentUser @Inject constructor(
    private val loginManager: LoginManager,
    private val identityRepository: IdentityRepository
) : ResultInteractor<Unit, GetCurrentUser.UserProfileResult>() {

    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    // this guys should probably take in user id.
    override suspend fun doWork(params: Unit) = loginManager.getCurrentUserId()?.let {
        val profile = identityRepository.getUserProfile(it)

        // lets assume profile always exist if we call this use case for the sake of simplicity
        UserProfileResult.Success(profile!!.toUser())

    } ?: UserProfileResult.Error(R.string.generic_error_message)


    sealed class UserProfileResult {
        class Success(val user: User) : UserProfileResult()
        class Error(@StringRes val error: Int) : UserProfileResult()
    }
}