package com.x.projectxx.ui.splash

import android.os.Bundle
import androidx.activity.viewModels
import com.x.projectxx.R
import com.x.projectxx.application.authentication.LoginManager
import com.x.projectxx.application.extensions.observeNonNull
import com.x.projectxx.ui.BaseActivity
import com.x.projectxx.ui.chat.ChatActivity
import com.x.projectxx.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setUpObservers()
    }

    private fun setUpObservers() {
        this.observeNonNull(viewModel.authStatus) {
            when(it) {
                is LoginManager.AuthState.LoggedIn -> navigateToChat()
                is LoginManager.AuthState.LoggedOut -> navigateToLogin()
            }
        }
    }

    private fun navigateToLogin() {
        startActivity(LoginActivity.makeChatIntent(this))
    }

    private fun navigateToChat() {
        startActivity(ChatActivity.makeChatIntent(this))
    }

}