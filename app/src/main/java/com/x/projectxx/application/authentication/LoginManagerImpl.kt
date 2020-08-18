package com.x.projectxx.application.authentication

import com.facebook.AccessToken
import com.facebook.AccessTokenTracker
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.x.projectxx.data.identity.IdentityRepository
import com.x.projectxx.domain.user.mappers.toUser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LoginManagerImpl @Inject constructor(
    private val identityRepository: IdentityRepository): LoginManager {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var authStatus: LoginManager.AuthState? = null

    private var accessTokenTracker: AccessTokenTracker = object : AccessTokenTracker() {
        override fun onCurrentAccessTokenChanged(
            oldAccessToken: AccessToken?,
            currentAccessToken: AccessToken?
        ) {

            // This is how we know user has logged out if we use facebook login
            if (currentAccessToken == null) {

                auth.signOut()
                authStatus = LoginManager.AuthState.LoggedOut()
            }
        }
    }


    private suspend fun initLoginStatusWithFireBaseUser(firebaseUser: FirebaseUser) {
        val userProfile = getUserProfile(firebaseUser.uid) ?: createUserProfile(firebaseUser)
        authStatus = LoginManager.AuthState.LoggedIn(userProfile.toUser())
    }

    override suspend fun getUserLoginStatus(): LoginManager.AuthState {
        return if(authStatus != null) {
            authStatus!!
        } else {
            // User is logged in but have not initialise user profile yet
            if(auth.currentUser != null) {
                // Login and initialise user profile
                initLoginStatusWithFireBaseUser((auth.currentUser!!))
            } else {
                authStatus = LoginManager.AuthState.LoggedOut()
            }
            authStatus!!
        }
    }

    // TO-DO We will get this current user from SessionManager down the track
    override fun getCurrentUserId() = auth.currentUser?.uid

    override suspend fun loginWithFacebookToken(token: AccessToken): LoginManager.AuthState =
        suspendCoroutine { cont ->
            val credential = FacebookAuthProvider.getCredential(token.token)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        auth.currentUser?.let { firebaseUser ->
                            GlobalScope.launch {
                                initLoginStatusWithFireBaseUser(firebaseUser)

                                cont.resume(authStatus!!)
                                accessTokenTracker.startTracking()
                            }
                        }
                    } else {
                        authStatus = LoginManager.AuthState.LoggedOut("Unable to sign-in")
                        cont.resume(authStatus!!)
                    }
                }
        }


    private suspend fun createUserProfile(firebaseUser: FirebaseUser) =
        identityRepository.createUserProfile(firebaseUser.toUserProfile())

    private suspend fun getUserProfile(userId: String) =
        identityRepository.getUserProfile(userId)
}
