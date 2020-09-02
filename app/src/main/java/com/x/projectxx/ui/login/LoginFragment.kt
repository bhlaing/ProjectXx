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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.x.projectxx.R
import com.x.projectxx.application.extensions.observeEvent
import com.x.projectxx.databinding.FragmentLoginBinding
import com.x.projectxx.ui.chat.ChatActivity
import com.x.projectxx.ui.login.model.LoginState
import com.x.projectxx.ui.login.model.LoginToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val callbackManager = CallbackManager.Factory.create()
    private lateinit var binding: FragmentLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        private const val RC_SIGN_IN = 100
    }

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
        binding.googleLogIn.setOnClickListener { logInWithGoogle() }
        binding.loginFacebookButton.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    viewModel.onLoginSuccess(LoginToken.FacebookToken(loginResult))
                }

                override fun onCancel() {}

                override fun onError(error: FacebookException) {}
            })

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        activity?.let { activity -> googleSignInClient = GoogleSignIn.getClient(activity,gso); }

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

                navigateToChatScreen()
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
        //If Google Sign In
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                viewModel.onLoginSuccess(LoginToken.GoogleToken(account.idToken!!))
            } catch (e: ApiException) {

            }
        }
    }

    private fun logInWithGoogle(){
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun navigateToChatScreen() =
        startActivity(ChatActivity.makeChatIntent(requireContext()))
}