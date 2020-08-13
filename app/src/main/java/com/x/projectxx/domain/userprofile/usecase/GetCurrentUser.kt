package com.x.projectxx.domain.userprofile.usecase

import com.x.projectxx.R
import com.x.projectxx.application.authentication.LoginManager
import javax.inject.Inject

class GetCurrentUser @Inject constructor(private val loginManager: LoginManager) {

    operator fun invoke(): UserProfileResult =
        loginManager.getCurrentUser()?.let {
            UserProfileResult.Success(it)
        } ?: UserProfileResult.Error(R.string.generic_error_message)

}