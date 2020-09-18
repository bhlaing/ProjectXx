package com.x.projectxx.ui.home.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.x.projectxx.application.extensions.observeNonNull
import com.x.projectxx.databinding.FragmentDashboardBinding
import com.x.projectxx.ui.content.addcontent.AddContentActivity
import com.x.projectxx.ui.content.model.UserContentItem
import com.x.projectxx.ui.home.dashboard.adapter.ContentsListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var contentsAdapter: ContentsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater)

        binding.addFab.setOnClickListener {
            startActivity(
                AddContentActivity.makeAddContentIntent(
                    requireContext()
                )
            )
        }

        setUpContentList()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
    }

    private fun setUpContentList() {
        contentsAdapter = ContentsListAdapter()
        binding.contentList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = contentsAdapter
        }
    }


    private fun setUpObservers() {
        viewLifecycleOwner.observeNonNull(viewModel.contents) {
            onUserContent(it)
        }
    }

    private fun onUserContent(contentItems: List<UserContentItem>) {
        contentsAdapter.updateContents(contentItems)
    }

}