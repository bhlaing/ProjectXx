package com.x.projectxx.ui.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.x.projectxx.R
import com.x.projectxx.databinding.ActivityChatBinding
import com.x.projectxx.ui.BaseActivity

class ChatActivity : BaseActivity() {

    companion object {
        fun makeChatIntent(context: Context) : Intent {
            return Intent(context, ChatActivity::class.java)
        }
    }

    private lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.chatFlowFragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
}