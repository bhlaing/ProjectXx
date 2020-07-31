package com.x.projectxx.global.login

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class LoginManagerImpl @Inject constructor(): LoginManager {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun loginWithFacebookToken(token: AccessToken) : LiveData<FirebaseUser> = liveData(Dispatchers.IO) {
            emit(_loginWithFacebookToken((token)))
        }

    private suspend fun _loginWithFacebookToken(token: AccessToken) = suspendCoroutine<FirebaseUser> { cont ->
            val credential = FacebookAuthProvider.getCredential(token.token)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful && auth.currentUser != null) {
                        cont.resumeWith(Result.success(auth.currentUser!!))
                    } else {
                        cont.resumeWithException(
                            task.exception ?: Exception("Oops something went wrong")
                        )
                    }
                }
        }
}