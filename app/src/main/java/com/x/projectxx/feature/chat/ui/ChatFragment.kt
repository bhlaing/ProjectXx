package com.x.projectxx.feature.chat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.x.projectxx.databinding.ChatFragmentBinding
import com.x.projectxx.feature.chat.adapter.ChatMessagesAdapter
import com.x.projectxx.global.extensions.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment(){

    private lateinit var binding: ChatFragmentBinding
    private val viewModel: ChatViewModel by viewModels()
    private lateinit var chatMessageAdapter: ChatMessagesAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ChatFragmentBinding.inflate(inflater)
        binding.sendButton.setOnClickListener {
            viewModel.messages
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setUpObservers()
    }

    private fun setupRecyclerView(){
        binding.messageList.apply {
            layoutManager = LinearLayoutManager(activity)
            chatMessageAdapter = ChatMessagesAdapter()
            adapter = chatMessageAdapter
        }
    }

    private fun setUpObservers(){
        viewLifecycleOwner.observe(viewModel.messages){ chatTranscript ->
            chatTranscript?.messages?.let {
                chatMessageAdapter.submitMessages(chatTranscript.messages)
            }
        }
    }

}