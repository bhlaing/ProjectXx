package com.x.projectxx.ui.settings

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.x.projectxx.domain.userprofile.GetCurrentUser
import com.x.projectxx.domain.userprofile.model.UserProfileResult

class MainSettingsViewModel @ViewModelInject constructor(private val getCurrentUser: GetCurrentUser) :
    ViewModel() {
    val currentUser: LiveData<UserProfileResult> = liveData { emit(getCurrentUser()) }
}