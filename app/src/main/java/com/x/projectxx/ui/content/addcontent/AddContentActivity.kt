package com.x.projectxx.ui.content.addcontent

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.x.projectxx.databinding.ActivityAddContentBinding
import com.x.projectxx.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddContentActivity : BaseActivity() {

    lateinit var binding: ActivityAddContentBinding
    companion object {
        fun makeAddContentIntent(context: Context) = Intent(context, AddContentActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showBackButton(binding.toolbar)
    }
}