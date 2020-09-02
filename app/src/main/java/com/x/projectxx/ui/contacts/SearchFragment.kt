package com.x.projectxx.ui.contacts

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.view.View.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.squareup.picasso.Picasso
import com.x.projectxx.R
import com.x.projectxx.application.extensions.observeNonNull
import com.x.projectxx.application.extensions.setTextOrGone
import com.x.projectxx.application.extensions.showShortToast
import com.x.projectxx.databinding.*
import com.x.projectxx.ui.contacts.model.SearchState
import com.x.projectxx.ui.contacts.model.ContactProfileItem
import com.x.projectxx.ui.contacts.model.ContactProfileItem.*
import com.x.projectxx.ui.contacts.model.UserActionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.view_profile_add.view.*

@AndroidEntryPoint
class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // need to hide menu options so this is needed
        setHasOptionsMenu(true)
        binding = FragmentSearchBinding.inflate(inflater)

        binding.searchButton.setOnClickListener { viewModel.onSearch(binding.inputEmail.text.toString()) }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
    }

    private fun setUpObservers() {
        viewLifecycleOwner.observeNonNull(viewModel.searchResult) { onSearchStateChanged(it) }

        viewLifecycleOwner.observeNonNull(viewModel.actionResult) { onActionResultChanged(it) }
    }

    private fun onActionResultChanged(actionState: UserActionState) {
        when (actionState) {
            is UserActionState.Loading -> {
                binding.loading.visibility = VISIBLE
            }
            is UserActionState.Success -> {
                binding.loading.visibility = GONE
                actionState.message?.let { context?.showShortToast(getString(it)) }
            }

            is UserActionState.Fail -> {
                binding.loading.visibility = GONE
                actionState.error?.let { context?.showShortToast(getString(it)) }
            }
        }
    }

    private fun onSearchStateChanged(searchState: SearchState) {
        when (searchState) {
            is SearchState.Searching -> {
                binding.profileLayout.visibility = GONE
                binding.progressBar.visibility = VISIBLE
            }
            is SearchState.Success -> onSuccessSearchState(searchState.user)
            is SearchState.Fail -> onFailSearchState(searchState.error)
        }
    }

    private fun onSuccessSearchState(user: ContactProfileItem) {
        resetProfileLayout()

        when (user) {
            is PendingContact -> showPendingContact(user)
            is RequestConfirmContact -> showRequestConfirmContact(user)
            is UnknownContact -> showUnknownContact(user)
            is ConfirmedContact -> showConfirmedContact(user)
        }
        binding.profileLayout.visibility = VISIBLE

        binding.progressBar.visibility = GONE
    }

    private fun resetProfileLayout() {
        val profileContainer = binding.profileLayout
        profileContainer.removeAllViews()
    }

    private fun showPendingContact(user: PendingContact) {
        val profileContainer = binding.profileLayout

        val binding = ViewProfilePendingBinding.inflate(
            LayoutInflater.from(profileContainer.context),
            profileContainer,
            true
        )

        binding.apply {
            this.nameText.text = user.displayName
            this.statusText.setTextOrGone(user.status)
            if (!user.image.isNullOrEmpty()) {
                Picasso.get().load(user.image).into(this.profileImage)
            }

            this.cancelButton.setOnClickListener { showConfirmCancelRequestDialog() }
        }
    }

    private fun showRequestConfirmContact(user: RequestConfirmContact) {
        val profileContainer = binding.profileLayout

        val binding = ViewProfileRequestBinding.inflate(
            LayoutInflater.from(profileContainer.context),
            profileContainer,
            true
        )

        binding.apply {
            this.nameText.text = user.displayName
            this.statusText.setTextOrGone(user.status)
            if (!user.image.isNullOrEmpty()) {
                Picasso.get().load(user.image).into(this.profileImage)
            }

            this.positiveButton.setOnClickListener { showConfirmAcceptDialog() }
        }
    }

    private fun showUnknownContact(user: UnknownContact) {
        val profileContainer = binding.profileLayout

        val binding = ViewProfileAddBinding.inflate(
            LayoutInflater.from(profileContainer.context),
            profileContainer,
            true
        )

        binding.apply {
            this.nameText.text = user.displayName
            this.statusText.setTextOrGone(user.status)
            if (!user.image.isNullOrEmpty()) {
                Picasso.get().load(user.image).into(this.profileImage)
            }
        }

        binding.profileAddContainer.actionButton.setOnClickListener { showConfirmAddDialog() }
    }

    private fun showConfirmedContact(user: ConfirmedContact) {
        val profileContainer = binding.profileLayout

        val binding = ViewProfileConfirmedBinding.inflate(
            LayoutInflater.from(profileContainer.context),
            profileContainer,
            true
        )

        binding.apply {
            this.nameText.text = user.displayName
            this.statusText.setTextOrGone(user.status)
            if (!user.image.isNullOrEmpty()) {
                Picasso.get().load(user.image).into(this.profileImage)
            }
        }
    }

    private fun onFailSearchState(error: Int?) {
        error?.let { binding.inputLayoutEmail.error = getString(it) }
        binding.progressBar.visibility = GONE
        binding.profileLayout.visibility = INVISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    private fun showConfirmCancelRequestDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.cancel_request_title)
            .setMessage(R.string.cancel_request_body)
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton(R.string.confirm)
            { _, _ -> viewModel.onCancelContact() }
            .setNegativeButton(R.string.no, null)
            .setIcon(R.drawable.ic_person_add_24)
            .show()
    }


    private fun showConfirmAcceptDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.confirm_accept_dialog_title)
            .setMessage(R.string.confirm_accept_dialog_body)
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton(R.string.confirm)
            { _, _ -> viewModel.onAcceptContact() }
            .setNegativeButton(R.string.cancel, null)
            .setIcon(R.drawable.ic_person_add_24)
            .show()
    }

    private fun showConfirmAddDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.add_contact_dialog_title)
            .setMessage(R.string.add_contact_dialog_body)
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton(R.string.add_contact)
            { _, _ -> viewModel.onAddContact() }
            .setNegativeButton(R.string.cancel, null)
            .setIcon(R.drawable.ic_person_add_24)
            .show()
    }
}