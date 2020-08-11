package com.x.projectxx.application.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

class LoginManagerImpl @Inject constructor(
    private val identityService: IdentityService): LoginManager {
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
        // TO-DO this auth state is tied to App initialisation card
        // Once app is initialised User should never be null!
        LoginManager.AuthState.LoggedIn(it.toUserProfile().toUser())
    } ?: LoginManager.AuthState.LoggedOut("")

    override fun getUserLoginStatus(): LiveData<LoginManager.AuthState> = if(authStatus.value != null) {
        authStatus
    } else {
        authStatus.apply {
            value = getAuthStatusFromFireBase() }
    }

    // TO-DO We will get this current user from SessionManager down the track
    override fun getCurrentUser() = auth.currentUser?.toUserProfile()?.toUser()

    override fun loginWithFacebookToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth.currentUser?.let { firebaseUser ->

                        GlobalScope.launch {
                            val userProfile = getUserProfile(firebaseUser.uid) ?: createUserProfile(firebaseUser)

                            authStatus.postValue(LoginManager.AuthState.LoggedIn(userProfile.toUser()))

                            accessTokenTracker.startTracking()
                        }
                    }
                } else {
                    authStatus.value = LoginManager.AuthState.LoggedOut("Unable to sign-in")
                }
            }
    }


    private suspend fun createUserProfile(firebaseUser: FirebaseUser) =
        identityService.createUserProfile(firebaseUser)

    private suspend fun getUserProfile(userId: String) =
        identityService.getUserProfile(userId)
}
