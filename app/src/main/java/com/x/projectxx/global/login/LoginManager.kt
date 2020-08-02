package com.x.projectxx.global.login

import androidx.lifecycle.LiveData
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseUser

interface LoginManager {
    fun getUserLoginStatus(): LiveData<AuthState>
    fun getFacebookUser(): FirebaseUser?
    fun loginWithFacebookToken(token: AccessToken)

    sealed class AuthState {
        class LoggedOut(val error: String? = null): AuthState()
        class LoggedIn(val firebaseUser: FirebaseUser): AuthState()
    }
}