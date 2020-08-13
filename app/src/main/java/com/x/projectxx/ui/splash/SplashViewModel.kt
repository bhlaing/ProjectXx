package com.x.projectxx.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.x.projectxx.application.authentication.LoginManager

class SplashViewModel @ViewModelInject constructor(private val loginManager: LoginManager): ViewModel() {

    val authStatus: LiveData<LoginManager.AuthState> = liveData {
        loginManager.getUserLoginStatus()?.let { emit(it) }
    }
}