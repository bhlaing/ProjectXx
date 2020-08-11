package com.x.projectxx.domain.userprofile.usecase

import androidx.annotation.StringRes
import com.x.projectxx.data.identity.userprofile.UserProfile
import com.x.projectxx.domain.userprofile.model.User

sealed class UserProfileResult {
    class Success(val user: User) : UserProfileResult()
    class Error(@StringRes val error: Int) : UserProfileResult()
}