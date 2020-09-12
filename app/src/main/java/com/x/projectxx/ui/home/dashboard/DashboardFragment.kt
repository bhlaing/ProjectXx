package com.x.projectxx.ui.home.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.x.projectxx.R
import com.x.projectxx.databinding.FragmentDashboardBinding
import com.x.projectxx.ui.content.addcontent.AddContentActivity

class DashboardFragment : Fragment() {

    private  val dashboardViewModel: DashboardViewModel by viewModels()
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater)
        val textView = binding.textHome

        binding.addFab.setOnClickListener {
            startActivity(AddContentActivity.makeAddContentIntent(requireContext()))
        }

        dashboardViewModel.text.observe(viewLifecycleOwner, Observer { textView.text = it })


        return binding.root
    }
}