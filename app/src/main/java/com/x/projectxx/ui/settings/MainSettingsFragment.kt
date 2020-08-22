package com.x.projectxx.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.squareup.picasso.Picasso
import com.x.projectxx.application.extensions.observeNonNull
import com.x.projectxx.application.extensions.setTextOrGone
import com.x.projectxx.application.extensions.showLongToast
import com.x.projectxx.databinding.FragmentSettingsBinding
import com.x.projectxx.domain.user.GetCurrentUser.UserProfileResult
import com.x.projectxx.domain.user.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainSettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    private val viewModel: MainSettingsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
    }

    private fun setUpObservers() {
        viewLifecycleOwner.observeNonNull(viewModel.currentUser) { onUserProfileResult(it) }
    }

    private fun onUserProfileResult(userProfileResult: UserProfileResult) =
        when(userProfileResult) {
            is UserProfileResult.Success -> {
                updateUserProfile(userProfileResult.user)
            }
            is UserProfileResult.Error -> {
                context?.run { showLongToast(getString(userProfileResult.error)) }
            }
        }

    private fun updateUserProfile(userProfile: User) {
        binding.profileLayout.nameText.text = userProfile.displayName
        binding.profileLayout.statusText.setTextOrGone(userProfile.status)

        userProfile.image?.run {
            Picasso.get().load(this).into(binding.profileLayout.profileImage)
        }
    }
}