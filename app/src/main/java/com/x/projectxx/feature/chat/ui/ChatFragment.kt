package com.x.projectxx.feature.chat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.x.projectxx.databinding.ChatFragmentBinding

class ChatFragment() : Fragment(){

    private lateinit var binding: ChatFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ChatFragmentBinding.inflate(inflater)
        return binding.root
    }
}