package com.x.projectxx.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.x.projectxx.R
import com.x.projectxx.databinding.ActivityLoginBinding
import com.x.projectxx.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    companion object {
        private const val TAG = "Login Activity"

        fun makeLoginIntent(context: Context) : Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.loginFlowFragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        title = getString(R.string.sign_up_title)
    }
}