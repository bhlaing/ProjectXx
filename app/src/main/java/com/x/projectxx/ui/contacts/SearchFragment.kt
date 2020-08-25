package com.x.projectxx.ui.contacts

import android.R
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.view.View.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.squareup.picasso.Picasso
import com.x.projectxx.application.extensions.observeNonNull
import com.x.projectxx.application.extensions.setTextOrGone
import com.x.projectxx.databinding.FragmentSearchBinding
import com.x.projectxx.ui.contacts.model.SearchState
import com.x.projectxx.ui.contacts.model.ContactProfileItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.view_profile_search.view.*

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

        binding.profileLayout.profileSearchContainer.statusIcon.setOnClickListener { showConfirmationDialog()  }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
    }

    private fun setUpObservers() {
        viewLifecycleOwner.observeNonNull(viewModel.searchResult) { searchState ->
            when (searchState) {
                is SearchState.Searching ->{
                    binding.profileLayout.root.visibility = GONE
                    binding.progressBar.visibility = VISIBLE
                }
                is SearchState.Success -> onSuccessSearchState(searchState.user)
                is SearchState.Fail -> onFailSearchState(searchState.error)
            }
        }
    }

    private fun onSuccessSearchState(user: ContactProfileItem) {
        binding.profileLayout.apply {
            this.nameText.text = user.displayName
            this.statusText.setTextOrGone(user.status)
            this.statusIcon.setImageResource(user.icon)
            if (!user.image.isNullOrEmpty()) {
                Picasso.get().load(user.image).into(this.profileImage)
            }
        }

        binding.profileLayout.root.visibility = VISIBLE

        binding.progressBar.visibility = GONE
    }

    private fun onFailSearchState(error: Int?) {
        error?.let { binding.inputLayoutEmail.error = getString(it) }
        binding.progressBar.visibility = GONE
        binding.profileLayout.root.visibility = INVISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Add contact")
            .setMessage("Please confirm to request as contact ")
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton(R.string.yes)
            { _, _ -> viewModel.onStatusAction() }
            .setNegativeButton(R.string.no, null)
            .setIcon(R.drawable.ic_dialog_alert)
            .show()
    }
}