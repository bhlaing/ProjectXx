package com.x.projectxx.global.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.facebook.AccessToken
import com.facebook.AccessTokenTracker
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class LoginManagerImpl @Inject constructor(): LoginManager {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val authStatus: MutableLiveData<LoginManager.AuthState> = MutableLiveData()

    private var accessTokenTracker: AccessTokenTracker = object : AccessTokenTracker() {
        override fun onCurrentAccessTokenChanged(
            oldAccessToken: AccessToken?,
            currentAccessToken: AccessToken?
        ) {

            // This is how we know user has logged out if we use facebook login
            if (currentAccessToken == null) {

                auth.signOut()
                authStatus.value = LoginManager.AuthState.LoggedOut()
            }
        }
    }

    private fun getAuthStatusFromFireBase() = auth.currentUser?.let {
        LoginManager.AuthState.LoggedIn(it)
    } ?: LoginManager.AuthState.LoggedOut("")

    override fun getUserLoginStatus(): LiveData<LoginManager.AuthState> = if(authStatus.value != null) {
        authStatus
    } else {
        authStatus.apply { value = getAuthStatusFromFireBase() }
    }

    override fun getFacebookUser() = auth.currentUser

    override fun loginWithFacebookToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful && auth.currentUser != null) {
                    auth.currentUser?.run {
                        authStatus.value = LoginManager.AuthState.LoggedIn(this)
                        accessTokenTracker.startTracking()

                    }
                } else {
                    authStatus.value = LoginManager.AuthState.LoggedOut("Unable to log in")
                }
            }
    }
}
