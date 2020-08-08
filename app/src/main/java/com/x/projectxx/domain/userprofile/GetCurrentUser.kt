package com.x.projectxx.domain.userprofile

import com.x.projectxx.R
import com.x.projectxx.application.authentication.LoginManager
import com.x.projectxx.application.authentication.userprofile.mapper.toUserProfile
import com.x.projectxx.domain.userprofile.model.UserProfileResult
import javax.inject.Inject

class GetCurrentUser @Inject constructor(private val loginManager: LoginManager) {

    operator fun invoke(): UserProfileResult =
        loginManager.getCurrentUser()?.let {
            UserProfileResult.Success(it.toUserProfile())
        } ?: UserProfileResult.Error(R.string.generic_error_message)

}