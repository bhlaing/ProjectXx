package com.x.projectxx.ui.home.contacts

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.x.projectxx.R
import com.x.projectxx.application.extensions.observeNonNull
import com.x.projectxx.application.extensions.showLongToast
import com.x.projectxx.databinding.FragmentContactsBinding
import com.x.projectxx.ui.home.contacts.adapter.ContactListAdapter
import com.x.projectxx.ui.home.contacts.model.ActionState
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
        setHasOptionsMenu(true)

        setUpObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpContactListView()
    }

//    override fun onResume() {
//        super.onResume()
//        viewModel.refresh()
//    }

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

        viewLifecycleOwner.observeNonNull(viewModel.actionState) {
            onActionState(it)
        }
    }

    private fun onActionState(actionState: ActionState) =
        when (actionState) {
            is ActionState.Loading -> binding.loadingWheel.visibility = View.VISIBLE

            is ActionState.Fail -> actionState.error?.let {
                requireContext().showLongToast(getString(it))
            }.also { binding.loadingWheel.visibility = View.GONE }

            is ActionState.Success -> actionState.message?.let {
                requireContext().showLongToast(getString(it))
            }.also { binding.loadingWheel.visibility = View.GONE }
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()

        inflater.inflate(R.menu.menu_contact, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.addContactItem -> startActivity(SearchActivity.makeSearchIntent(requireContext()))
        }

        return super.onOptionsItemSelected(item)
    }

}