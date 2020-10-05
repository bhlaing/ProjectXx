package com.x.projectxx.ui.content.selectcontent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.x.projectxx.application.extensions.observeNonNull
import com.x.projectxx.databinding.FragmentSelectContentTypeBinding
import com.x.projectxx.ui.content.selectcontent.adapter.MyContentTypeRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectContentTypeFragment : Fragment() {

    private val viewModel: SelectContentTypeViewModel by viewModels()
    private lateinit var binding: FragmentSelectContentTypeBinding
    private lateinit var contentTypeAdapter: MyContentTypeRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectContentTypeBinding.inflate(inflater)

        setUpContentTypeList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
    }

    private fun setUpContentTypeList() {
        contentTypeAdapter = MyContentTypeRecyclerViewAdapter()
        binding.contentTypeList.apply {
            adapter = contentTypeAdapter

            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setUpObservers() {
        viewLifecycleOwner.observeNonNull(viewModel.contentsTypes) {
            contentTypeAdapter.updateContentTypes(it)
        }
    }
}