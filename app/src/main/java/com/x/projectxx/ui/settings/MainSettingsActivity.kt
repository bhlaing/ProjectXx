package com.x.projectxx.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.x.projectxx.R
import com.x.projectxx.databinding.ActivitySettingsBinding
import com.x.projectxx.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainSettingsActivity: BaseActivity() {

    companion object {
        fun makeChatIntent(context: Context) : Intent {
            return Intent(context, MainSettingsActivity::class.java)
        }
    }

    lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.settingsFlowFragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        showBackButton(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }
}