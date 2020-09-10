package com.x.projectxx.ui.home.contacts.model

import androidx.annotation.StringRes

sealed class ActionState {
    object Loading: ActionState()
    class Success(@StringRes val message: Int? = null): ActionState()
    class Fail(val error: Int?): ActionState()
}