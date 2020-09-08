package com.x.projectxx.ui.home.contacts.model

import androidx.annotation.StringRes

sealed class SearchState {
    object Searching: SearchState()
    class Success(val user: ContactProfileItem): SearchState()
    class Fail(@StringRes val error: Int?): SearchState()
}