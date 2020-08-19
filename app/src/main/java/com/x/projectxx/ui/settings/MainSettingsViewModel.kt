package com.x.projectxx.ui.settings

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.x.projectxx.domain.user.GetCurrentUser

class MainSettingsViewModel @ViewModelInject constructor(private val getCurrentUser: GetCurrentUser) :
    ViewModel() {
    val currentUser: LiveData<GetCurrentUser.UserProfileResult> = liveData { emit(getCurrentUser(Unit)) }
}