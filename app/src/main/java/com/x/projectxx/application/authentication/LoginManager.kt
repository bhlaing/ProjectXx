package com.x.projectxx.application.authentication

import androidx.lifecycle.LiveData
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseUser
import com.x.projectxx.application.authentication.userprofile.UserProfile

interface LoginManager {
    fun getUserLoginStatus(): LiveData<AuthState>
    fun getFacebookUser(): FirebaseUser?
    fun loginWithFacebookToken(token: AccessToken)

    sealed class AuthState {
        class LoggedOut(val error: String? = null): AuthState()
        class LoggedIn(val userProfile: UserProfile): AuthState()
    }
}