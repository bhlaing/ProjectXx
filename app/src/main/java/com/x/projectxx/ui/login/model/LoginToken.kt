package com.x.projectxx.ui.login.model

import com.facebook.login.LoginResult

sealed class LoginToken{
    class FacebookToken(var loginResult : LoginResult) : LoginToken()
    class GoogleToken(var idToken : String) : LoginToken()
}