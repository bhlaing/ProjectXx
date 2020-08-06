package com.x.projectxx.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.facebook.AccessToken
import com.x.projectxx.application.extensions.Event
import com.x.projectxx.application.authentication.LoginManager

class LoginViewModel @ViewModelInject constructor(private val loginManager: LoginManager) : ViewModel() {

    val authState: LiveData<Event<LoginManager.AuthState>> = loginManager.getUserLoginStatus().map {
        Event(it)
    }

    fun onFacebookLoginSuccess(token: AccessToken) = loginManager.loginWithFacebookToken(token)
}
