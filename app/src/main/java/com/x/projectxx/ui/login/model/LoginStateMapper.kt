package com.x.projectxx.ui.login.model

import com.x.projectxx.application.authentication.LoginManager

fun LoginManager.AuthState.toLoginState() = mapAuthStateToLoginState(this)

private fun mapAuthStateToLoginState(authState: LoginManager.AuthState) =
    when(authState) {
        is LoginManager.AuthState.LoggedIn -> LoginState.Success
        is LoginManager.AuthState.LoggedOut -> LoginState.Failed(authState.error)
    }
