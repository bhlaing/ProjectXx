package com.x.projectxx.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.x.projectxx.application.extensions.observeEvent
import com.x.projectxx.application.authentication.LoginManager
import com.x.projectxx.databinding.FragmentLoginBinding
import com.x.projectxx.ui.chat.ChatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val callbackManager = CallbackManager.Factory.create()
    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.loginButton.setReadPermissions("email", "public_profile")
        binding.loginButton.fragment = this
        binding.loginButton.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    viewModel.onFacebookLoginSuccess(loginResult.accessToken)
                }

                override fun onCancel() {}

                override fun onError(error: FacebookException) {}
            })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
    }

    private fun setUpObservers() {
        viewLifecycleOwner.observeEvent(viewModel.authState) { authState ->
            onAuthStateChanged(authState)
        }
    }

    private fun onAuthStateChanged(authState: LoginManager.AuthState) =
        when (authState) {
            is LoginManager.AuthState.LoggedIn -> {
                navigateToChatScreen()

            }
            is LoginManager.AuthState.LoggedOut -> {
                binding.displayName.text = "ProjectxX"
            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun navigateToChatScreen() =
        startActivity(ChatActivity.makeChatIntent(requireContext()))
}