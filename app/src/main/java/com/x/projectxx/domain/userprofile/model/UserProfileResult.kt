package com.x.projectxx.domain.userprofile.model

import androidx.annotation.StringRes
import com.x.projectxx.application.authentication.userprofile.UserProfile

sealed class UserProfileResult {
    class Success(val userProfile: UserProfile) : UserProfileResult()
    class Error(@StringRes val error: Int) : UserProfileResult()
}