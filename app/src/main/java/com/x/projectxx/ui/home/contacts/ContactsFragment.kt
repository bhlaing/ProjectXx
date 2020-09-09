package com.x.projectxx.ui.home.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.x.projectxx.R
import com.x.projectxx.application.extensions.observeNonNull
import com.x.projectxx.databinding.FragmentContactsBinding
import com.x.projectxx.ui.home.contacts.adapter.ContactListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsFragment : Fragment() {
    private lateinit var binding: FragmentContactsBinding
    private lateinit var contactListAdapter: ContactListAdapter

    private val viewModel: ContactsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        binding.contactsListView.apply { layoutManager = LinearLayoutManager(requireContext()) }
        val separator =   DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL)
        separator.setDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.seperator_vertical_white)!!)


        binding.contactsListView.addItemDecoration(separator)

        binding.contactsListView.adapter = contactListAdapter
    }

    private fun setUpObservers() {
        viewLifecycleOwner.observeNonNull(viewModel.contactList) {
            contactListAdapter.updateContacts(it)
        }
    }
}