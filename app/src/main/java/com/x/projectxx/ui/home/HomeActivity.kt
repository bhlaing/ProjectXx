package com.x.projectxx.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
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