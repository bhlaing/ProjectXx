package com.x.projectxx.ui.contacts

import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.squareup.picasso.Picasso
import com.x.projectxx.application.extensions.observeNonNull
import com.x.projectxx.application.extensions.setTextOrGone
import com.x.projectxx.databinding.FragmentSearchBinding
import com.x.projectxx.domain.user.model.User
import com.x.projectxx.ui.contacts.model.SearchState
import dagger.hilt.android.AndroidEntryPoint

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
        viewLifecycleOwner.observeNonNull(viewModel.searchResult) { searchState ->
            when(searchState) {
                is SearchState.Searching -> binding.progressBar.visibility = VISIBLE
                is SearchState.Success -> onSuccessSearchState(searchState.user)
                is SearchState.Fail -> onFailSearchState(searchState.error)
            }
        }
    }

    private fun onSuccessSearchState(user: User) {
        binding.profileLayout.apply {
            this.nameText.text = user.displayName
            this.statusText.setTextOrGone(user.status)
            Picasso.get().load(user.image).into(this.profileImage)
        }

        binding.progressBar.visibility = GONE
    }

    private fun onFailSearchState(error: Int?) {
        error?.let { binding.inputLayoutEmail.error = getString(it) }
        binding.progressBar.visibility = GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        // hide the menu options
        menu.clear()
    }
}