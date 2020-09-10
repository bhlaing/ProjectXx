package com.x.projectxx.ui.login.model

import com.facebook.login.LoginResult

sealed class LoginToken{
    class FacebookToken(val loginResult : LoginResult) : LoginToken()
    class GoogleToken(val idToken : String) : LoginToken()
}