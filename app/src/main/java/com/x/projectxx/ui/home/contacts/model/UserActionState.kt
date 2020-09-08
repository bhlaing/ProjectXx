package com.x.projectxx.ui.home.contacts.model

import androidx.annotation.StringRes

sealed class UserActionState {
    object Loading: UserActionState()
    class Success(@StringRes val message: Int?): UserActionState()
    class Fail(val error: Int?): UserActionState()
}