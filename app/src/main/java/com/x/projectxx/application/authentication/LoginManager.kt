package com.x.projectxx.application.authentication

import androidx.lifecycle.LiveData
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseUser
import com.x.projectxx.data.identity.userprofile.UserProfile
import com.x.projectxx.domain.userprofile.model.User

interface LoginManager {
    fun getUserLoginStatus(): LiveData<AuthState>
    fun getCurrentUser(): User?
    fun loginWithFacebookToken(token: AccessToken)

    sealed class AuthState {
        class LoggedOut(val error: String? = null): AuthState()
        class LoggedIn(val user: User): AuthState()
    }
}