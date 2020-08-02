package com.x.projectxx.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseUser
import com.x.projectxx.global.login.LoginManager

class LoginViewModel @ViewModelInject constructor(private val loginManager: LoginManager) : ViewModel() {

    private val _authState = MutableLiveData<LoginManager.AuthState>()
    val authState: LiveData<LoginManager.AuthState> = _authState

    init {
        loginManager.setAuthStateChangeListener {
            _authState.value = it
        }
    }

    fun onFacebookLoginSuccess(token: AccessToken) = loginManager.loginWithFacebookToken(token)
}