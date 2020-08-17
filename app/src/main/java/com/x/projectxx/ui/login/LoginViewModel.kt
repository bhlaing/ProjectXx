package com.x.projectxx.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.facebook.AccessToken
import com.x.projectxx.application.extensions.Event
import com.x.projectxx.application.authentication.LoginManager
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(private val loginManager: LoginManager) :
    ViewModel() {

    private val _authState: MutableLiveData<Event<LoginManager.AuthState>> = MutableLiveData()
    val authState: LiveData<Event<LoginManager.AuthState>> = _authState

    fun onFacebookLoginSuccess(token: AccessToken) =
        viewModelScope.launch {
            val loginResult = loginManager.loginWithFacebookToken(token)
            _authState.postValue(Event(loginResult))
        }
}
