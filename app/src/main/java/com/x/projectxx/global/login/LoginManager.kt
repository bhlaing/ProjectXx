package com.x.projectxx.global.login

import androidx.lifecycle.LiveData
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseUser

interface LoginManager {

    fun loginWithFacebookToken(token: AccessToken): LiveData<FirebaseUser>
}