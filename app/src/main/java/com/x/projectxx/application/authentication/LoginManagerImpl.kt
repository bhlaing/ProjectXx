package com.x.projectxx.application.authentication

import com.facebook.AccessToken
import com.facebook.AccessTokenTracker
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.x.projectxx.data.identity.IdentityService
import com.x.projectxx.domain.userprofile.toUser
import com.x.projectxx.domain.userprofile.toUserProfile
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LoginManagerImpl @Inject constructor(
    private val identityService: IdentityService): LoginManager {
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


    private suspend fun initLoginStatusWithFireBaseuser(firebaseUser: FirebaseUser) {
        val userProfile = getUserProfile(firebaseUser.uid) ?: createUserProfile(firebaseUser)
        authStatus = LoginManager.AuthState.LoggedIn(userProfile.toUser())
    }

    override suspend fun getUserLoginStatus(): LoginManager.AuthState? {
        if(authStatus != null) {
            return authStatus
        } else {
            // User is logged in but have not initialise user profile yet
            if(auth.currentUser != null) {
                // Login and initialise user profile
                initLoginStatusWithFireBaseuser((auth.currentUser!!))
            } else {
                authStatus = LoginManager.AuthState.LoggedOut()
            }
        }

        return authStatus;
    }

    // TO-DO We will get this current user from SessionManager down the track
    override fun getCurrentUser() = auth.currentUser?.toUserProfile()?.toUser()

    override suspend fun loginWithFacebookToken(token: AccessToken): LoginManager.AuthState =
        suspendCoroutine<LoginManager.AuthState> {
            val credential = FacebookAuthProvider.getCredential(token.token)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        auth.currentUser?.let { firebaseUser ->
                            GlobalScope.launch {
                                initLoginStatusWithFireBaseuser(firebaseUser)
                                it.resume(authStatus!!)
                                accessTokenTracker.startTracking()
                            }
                        }
                    } else {
                        authStatus = LoginManager.AuthState.LoggedOut("Unable to sign-in")
                        it.resume(authStatus!!)
                    }
                }
        }


    private suspend fun createUserProfile(firebaseUser: FirebaseUser) =
        identityService.createUserProfile(firebaseUser)

    private suspend fun getUserProfile(userId: String) =
        identityService.getUserProfile(userId)
}
