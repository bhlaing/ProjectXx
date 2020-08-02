package com.x.projectxx.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.facebook.AccessToken
import com.x.projectxx.global.login.LoginManager

class LoginViewModel @ViewModelInject constructor(private val loginManager: LoginManager) : ViewModel() {
    val authState: LiveData<LoginManager.AuthState> = loginManager.getUserLoginStatus()
    fun onFacebookLoginSuccess(token: AccessToken) = loginManager.loginWithFacebookToken(token)
}