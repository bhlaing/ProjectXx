package com.x.projectxx.ui.home.contacts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.x.projectxx.domain.contact.RetrieveContactList
import com.x.projectxx.domain.contact.model.ContactDetails
import com.x.projectxx.domain.user.GetCurrentUser
import com.x.projectxx.domain.user.GetCurrentUser.*
import com.x.projectxx.domain.user.model.User

class ContactsViewModel @ViewModelInject constructor(
    private val getCurrentUser: GetCurrentUser,
    private val retrieveContactList: RetrieveContactList
) : ViewModel() {

    private val observeCurrentUserProfile: LiveData<User> =
        liveData {
            when(val result = getCurrentUser(Unit)) {
                is UserProfileResult.Success -> emit(result.user)
                is UserProfileResult.Error -> Unit // Don't do anything
            }
        }

    val contactList: LiveData<List<ContactDetails>> = observeCurrentUserProfile.switchMap { user ->
        liveData {

            when(val result =  retrieveContactList(RetrieveContactList.Param(user.userId))){
                is RetrieveContactList.Result.Success -> emit(result.contacts)
            }
        }
    }
}