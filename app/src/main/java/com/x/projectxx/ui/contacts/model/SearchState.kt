package com.x.projectxx.ui.contacts.model

import androidx.annotation.StringRes
import com.x.projectxx.domain.user.model.User

sealed class SearchState {
    object Searching: SearchState()
    class Success(val user: User): SearchState()
    class Fail(@StringRes val error: Int?): SearchState()
}