package com.x.projectxx.ui.content.addcontent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.x.projectxx.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddContentFragment : Fragment() {

    companion object {
        fun newInstance() =
            AddContentFragment()
    }

    private val viewModel: AddContentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_add_content, container, false)
    }

}