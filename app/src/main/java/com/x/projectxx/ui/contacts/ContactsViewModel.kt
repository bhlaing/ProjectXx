package com.x.projectxx.ui.contacts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.x.projectxx.domain.user.model.ContactDetails
import com.x.projectxx.domain.user.GetContactDetails
import com.x.projectxx.domain.user.GetContactDetails.*
import com.x.projectxx.domain.user.GetCurrentUser
import com.x.projectxx.domain.user.GetCurrentUser.*
import com.x.projectxx.domain.user.model.User

class ContactsViewModel @ViewModelInject constructor(
    private val getCurrentUser: GetCurrentUser,
    private val getContactDetails: GetContactDetails
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
            val contactDetails =  retrieveContactDetails(user.contacts).map { it.contactDetails }
            emit(contactDetails)
        }
    }

    private suspend fun retrieveContactDetails(contacts: List<User.Contact>) = contacts.map {
            val contactDetailsParam = Param(it.userId)

            getContactDetails(contactDetailsParam).takeIf { result ->
                result is ContactDetailsResult.Success
            } as ContactDetailsResult.Success
        }
}