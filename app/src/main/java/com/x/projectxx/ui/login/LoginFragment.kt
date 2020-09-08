package com.x.projectxx.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.x.projectxx.application.extensions.observeEvent
import com.x.projectxx.databinding.FragmentLoginBinding
import com.x.projectxx.ui.home.HomeActivity
import com.x.projectxx.ui.login.model.LoginState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val callbackManager = CallbackManager.Factory.create()
    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.loginFacebookButton.setReadPermissions("email", "public_profile")
        binding.loginFacebookButton.fragment = this
        binding.facebookIcon.setOnClickListener { binding.loginFacebookButton.callOnClick() }
        binding.loginFacebookButton.registerCallback(
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

    private fun onAuthStateChanged(loginState: LoginState) =
        when (loginState) {
            is LoginState.Loading -> {
                binding.loginFacebookButton.visibility = INVISIBLE
                binding.loginToContinue.visibility = INVISIBLE

                binding.loading.visibility = VISIBLE
            }
            is LoginState.Success -> {
                binding.loading.visibility = GONE

                navigateToHomeScreen()
            }
            is LoginState.Failed -> {
                binding.loading.visibility = GONE

                binding.loginFacebookButton.visibility = VISIBLE
                binding.loginToContinue.visibility = VISIBLE

                binding.loginToContinue.text = loginState.error

            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun navigateToHomeScreen() = startActivity(HomeActivity.makeHomeIntent(requireContext()))
}