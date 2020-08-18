package com.x.projectxx.ui.contacts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.x.projectxx.R
import com.x.projectxx.databinding.ActivityContactsBinding
import com.x.projectxx.databinding.ActivitySettingsBinding
import com.x.projectxx.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsActivity: BaseActivity() {

    companion object {
        fun makeContactsIntent(context: Context) : Intent {
            return Intent(context, ContactsActivity::class.java)
        }
    }

    lateinit var binding: ActivityContactsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.contactsFlowFragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        showBackButton(binding.toolbar)
        title = getString(R.string.contacts)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }
}