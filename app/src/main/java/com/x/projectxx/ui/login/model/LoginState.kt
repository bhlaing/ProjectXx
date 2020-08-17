package com.x.projectxx.ui.login.model

sealed class LoginState {
    object Loading : LoginState()
    object Success : LoginState()
    class Failed(val error: String?) : LoginState()
}