package com.x.projectxx.application.authentication

import com.facebook.AccessToken
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.x.projectxx.data.identity.IdentityRepository
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LoginManagerImpl @Inject constructor(
    private val identityRepository: IdentityRepository
) : LoginManager {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

//    private var accessTokenTracker: AccessTokenTracker = object : AccessTokenTracker() {
//        override fun onCurrentAccessTokenChanged(
//            oldAccessToken: AccessToken?,
//            currentAccessToken: AccessToken?
//        ) {
//
//            // This is how we know user has logged out if we use facebook login
//            if (currentAccessToken == null) {
//
//                identityRepository.signOut()
//            }
//        }
//    }

    override suspend fun getUserLoginStatus(): LoginManager.AuthState {
        return auth.currentUser?.let{
            LoginManager.AuthState.LoggedIn
        } ?: LoginManager.AuthState.LoggedOut()
    }

    // TO-DO We will get this current user from SessionManager down the track
    override fun getCurrentUserId() = auth.currentUser?.uid

    /**
     * Use to sign up for ProjectXx user profile with Facebook access token
     * @param token Facebook AccessToken
     * @return AuthState.LoggedIn if sign up is successful and AuthState.LoggedOut
     * if sign up fails
     *
     */
    override suspend fun signUpWithFacebookToken(token: AccessToken): LoginManager.AuthState =
        suspendCoroutine { cont ->
            val credential = FacebookAuthProvider.getCredential(token.token)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        cont.resume(LoginManager.AuthState.LoggedIn)

                    } else {
                        cont.resume(LoginManager.AuthState.LoggedOut())
                    }
                }
        }

}
