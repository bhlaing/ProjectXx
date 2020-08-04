package com.x.projectxx.feature.login.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.facebook.AccessToken
import com.x.projectxx.global.extensions.Event
import com.x.projectxx.global.login.LoginManager

class LoginViewModel @ViewModelInject constructor(private val loginManager: LoginManager) : ViewModel() {

    val authState: LiveData<Event<LoginManager.AuthState>> = loginManager.getUserLoginStatus().map {
        Event(it)
    }

    fun onFacebookLoginSuccess(token: AccessToken) = loginManager.loginWithFacebookToken(token)
}
