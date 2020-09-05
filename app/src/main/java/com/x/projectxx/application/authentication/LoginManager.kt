package com.x.projectxx.application.authentication

import com.facebook.AccessToken
import com.x.projectxx.domain.user.model.User
import com.x.projectxx.ui.login.model.LoginToken

interface LoginManager {
    suspend fun getUserLoginStatus(): AuthState
    suspend fun signUpWithToken(token:LoginToken): AuthState

    fun getCurrentUserId(): String?

    sealed class AuthState {
        class LoggedOut(val error: String? = null): AuthState()
        object LoggedIn: AuthState()
    }
}