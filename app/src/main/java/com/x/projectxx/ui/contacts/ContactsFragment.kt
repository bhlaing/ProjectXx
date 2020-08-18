package com.x.projectxx.ui.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.squareup.picasso.Picasso
import com.x.projectxx.application.extensions.observeNonNull
import com.x.projectxx.application.extensions.showLongToast
import com.x.projectxx.databinding.FragmentContactsBinding
import com.x.projectxx.databinding.FragmentSettingsBinding
import com.x.projectxx.domain.userprofile.model.User
import com.x.projectxx.domain.userprofile.usecase.UserProfileResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsFragment : Fragment() {
    private lateinit var binding: FragmentContactsBinding

    private val viewModel: ContactsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentContactsBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
    }

    private fun setUpObservers() {
    }


}