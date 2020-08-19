package com.x.projectxx.ui.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.x.projectxx.application.extensions.observeNonNull
import com.x.projectxx.databinding.FragmentContactsBinding
import com.x.projectxx.ui.contacts.adapter.ContactListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsFragment : Fragment() {
    private lateinit var binding: FragmentContactsBinding
    private lateinit var contactListAdapter: ContactListAdapter

    private val viewModel: ContactsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentContactsBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        setUpContactListView()
    }

    private fun setUpContactListView() {
        contactListAdapter = ContactListAdapter()
        binding.contactsListView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = contactListAdapter
        }
    }

    private fun setUpObservers() {
        viewLifecycleOwner.observeNonNull(viewModel.contactList) {
            contactListAdapter.updateContacts(it)
        }
    }
}