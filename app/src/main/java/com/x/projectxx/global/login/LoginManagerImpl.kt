package com.x.projectxx.global.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.facebook.AccessToken
import com.facebook.AccessTokenTracker
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class LoginManagerImpl @Inject constructor(): LoginManager {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var authStatusChangeListener :((LoginManager.AuthState) -> Unit)? = null

    private var accessTokenTracker: AccessTokenTracker = object : AccessTokenTracker() {
        override fun onCurrentAccessTokenChanged(
            oldAccessToken: AccessToken?,
            currentAccessToken: AccessToken?
        ) {

            // This is how we know user has logged out if we use facebook login
            if (currentAccessToken == null) {
                auth.signOut()
                authStatusChangeListener?.let { it(LoginManager.AuthState.LoggedOut()) }
            }
        }
    }
    override fun isUserLoggedIn() = auth.currentUser != null

    override fun getFacebookUser() = auth.currentUser

    override fun setAuthStateChangeListener(listener: ((LoginManager.AuthState) -> Unit)?) {
        this.authStatusChangeListener = listener
    }

    override fun loginWithFacebookToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful && auth.currentUser != null) {
                    auth.currentUser?.run {
                        notifyLoginSuccess(this)
                    }
                    accessTokenTracker.startTracking()
                } else {
                    authStatusChangeListener?.let { it(LoginManager.AuthState.LoggedOut("Unable to log in")) }
                }
            }
    }

    private fun notifyLoginSuccess(firebaseUser: FirebaseUser) =
        authStatusChangeListener?.let {
            it(LoginManager.AuthState.LoggedIn(firebaseUser))
        }
}
    //    override fun loginWithFacebookToken(token: AccessToken) : LiveData<FirebaseUser> = liveData(Dispatchers.IO) {
//            emit(_loginWithFacebookToken((token)))
//        }


//    private suspend fun _loginWithFacebookToken(token: AccessToken) = suspendCoroutine<FirebaseUser> { cont ->
//            val credential = FacebookAuthProvider.getCredential(token.token)
//            auth.signInWithCredential(credential)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful && auth.currentUser != null) {
//
//                        auth.currentUser?.run {
//                            cont.resumeWith(Result.success(this))
//                            notifyLoginSuccess(this)
//                        }
//
//                        accessTokenTracker.startTracking()
//                    } else {
//                        cont.resumeWithException(
//                            task.exception ?: Exception("Oops something went wrong")
//                        )
//                    }
//                }
//        }

