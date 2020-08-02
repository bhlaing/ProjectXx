package com.x.projectxx.global.login

import androidx.lifecycle.LiveData
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseUser

interface LoginManager {
//    fun loginWithFacebookToken(token: AccessToken): LiveData<FirebaseUser>
    fun isUserLoggedIn(): Boolean
    fun getFacebookUser(): FirebaseUser?
    fun setAuthStateChangeListener(listener: ((AuthState) -> Unit)?)
    fun loginWithFacebookToken(token: AccessToken)

    sealed class AuthState {
        class LoggedOut(val error: String? = null): AuthState()
        class LoggedIn(val firebaseUser: FirebaseUser): AuthState()
    }
}