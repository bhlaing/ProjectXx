package com.x.projectxx.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.x.projectxx.R
import com.x.projectxx.databinding.ActivityHomeBinding
import com.x.projectxx.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    companion object {
        fun makeHomeIntent(context: Context) : Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }

    private lateinit var bindings: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bindings.root)

        setSupportActionBar(bindings.toolbar)

        setUpNavigation()
    }

    private fun setUpNavigation() {
        val bottomNavigationView = bindings.navView
        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(
            bottomNavigationView,
            navController
        )

        // prevent user from spam clicking the tabs
        bottomNavigationView.setOnNavigationItemReselectedListener { Unit }
    }
}