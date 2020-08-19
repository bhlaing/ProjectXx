package com.x.projectxx.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.x.projectxx.databinding.FragmentChatBinding
import com.x.projectxx.ui.chat.adapter.ChatMessagesAdapter
import com.x.projectxx.application.extensions.observeNonNull
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private val viewModel: ChatViewModel by viewModels()
    private lateinit var chatMessageAdapter: ChatMessagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMessageListView()
        setUpObservers()
    }

    private fun setUpMessageListView() {
        binding.messageList.apply {
            layoutManager = LinearLayoutManager(activity)
            chatMessageAdapter = ChatMessagesAdapter()
            adapter = chatMessageAdapter
        }
    }

    private fun setUpObservers() {
        viewLifecycleOwner.observeNonNull(viewModel.messages) {chatTranscript ->
            chatMessageAdapter.updateMessages(chatTranscript.messages)
        }
    }
}