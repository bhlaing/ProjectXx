package com.x.projectxx.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.facebook.AccessToken
import com.x.projectxx.application.extensions.Event
import com.x.projectxx.application.authentication.LoginManager
import com.x.projectxx.ui.login.model.LoginState
import com.x.projectxx.ui.login.model.toLoginState
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(private val loginManager: LoginManager) :
    ViewModel() {

    private val _authState: MutableLiveData<Event<LoginState>> = MutableLiveData()
    val authState: LiveData<Event<LoginState>> = _authState

    fun onFacebookLoginSuccess(token: AccessToken) =
        viewModelScope.launch {
            _authState.postValue(Event(LoginState.Loading))

            val loginResult = loginManager.signUpWithFacebookToken(token)
            _authState.postValue(Event(loginResult.toLoginState()))
        }
}
