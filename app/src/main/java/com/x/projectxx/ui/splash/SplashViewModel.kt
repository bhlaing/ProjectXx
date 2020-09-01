package com.x.projectxx.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.x.projectxx.application.authentication.LoginManager
import com.x.projectxx.data.contacts.ContactRepository
import com.x.projectxx.data.identity.IdentityRepository

class SplashViewModel @ViewModelInject constructor(private val loginManager: LoginManager): ViewModel() {

    val authStatus: LiveData<LoginManager.AuthState> = liveData {
        emit(loginManager.getUserLoginStatus())
    }
}