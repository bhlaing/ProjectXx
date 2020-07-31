package com.x.projectxx.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseUser
import com.x.projectxx.global.login.LoginManager

class LoginViewModel @ViewModelInject constructor(private val loginManager: LoginManager) : ViewModel() {

    private var accessTokenSuccess = MutableLiveData<AccessToken>()

    val user: LiveData<FirebaseUser> = accessTokenSuccess.switchMap { accessToken ->
        loginManager.loginWithFacebookToken(accessToken)
    }

    fun onFacebookLoginSuccess(token: AccessToken) {
        accessTokenSuccess.value = token
    }
}