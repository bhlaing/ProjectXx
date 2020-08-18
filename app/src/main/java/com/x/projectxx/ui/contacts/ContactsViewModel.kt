package com.x.projectxx.ui.contacts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.x.projectxx.domain.userprofile.usecase.GetCurrentUser
import com.x.projectxx.domain.userprofile.usecase.UserProfileResult

class ContactsViewModel @ViewModelInject constructor(private val getCurrentUser: GetCurrentUser) :
    ViewModel() {
    val currentUser: LiveData<UserProfileResult> = liveData { emit(getCurrentUser()) }
}