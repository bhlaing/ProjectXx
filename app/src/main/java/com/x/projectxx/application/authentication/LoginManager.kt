package com.x.projectxx.application.authentication

import com.facebook.AccessToken
import com.x.projectxx.domain.user.model.User

interface LoginManager {
    suspend fun getUserLoginStatus(): AuthState
    suspend fun loginWithFacebookToken(token: AccessToken): AuthState

    fun getCurrentUserId(): String?

    sealed class AuthState {
        class LoggedOut(val error: String? = null): AuthState()
        class LoggedIn(val user: User): AuthState()
    }
}