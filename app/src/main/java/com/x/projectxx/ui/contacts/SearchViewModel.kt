package com.x.projectxx.ui.contacts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.x.projectxx.application.authentication.LoginManager
import com.x.projectxx.domain.contact.AcceptContact
import com.x.projectxx.domain.contact.RetrieveUserContacts
import com.x.projectxx.domain.contact.RetrieveUserContacts.*
import com.x.projectxx.domain.contact.RetrieveUserContacts.Param as UserContactsParam
import com.x.projectxx.domain.user.SearchUserByEmail
import com.x.projectxx.domain.user.SearchUserByEmail.Param as SearchUserByEmailParam
import com.x.projectxx.domain.user.SearchUserByEmail.*
import com.x.projectxx.domain.user.model.User
import com.x.projectxx.ui.contacts.model.SearchState
import com.x.projectxx.ui.contacts.model.ContactProfileItem
import com.x.projectxx.ui.contacts.model.UserActionState
import com.x.projectxx.ui.contacts.model.toSearchUserProfileItem
import kotlinx.coroutines.launch
import com.x.projectxx.domain.contact.RequestContact as RequestContact
import com.x.projectxx.R
import timber.log.Timber

class SearchViewModel @ViewModelInject constructor(
    private val searchUserByEmail: SearchUserByEmail,
    private val requestContact: RequestContact,
    private val loginManager: LoginManager,
    private val retrieveUserContacts: RetrieveUserContacts,
    private val acceptContact: AcceptContact
) : ViewModel() {

    private val searchByEmailResult: MutableLiveData<SearchState> = MutableLiveData()
    val searchResult: LiveData<SearchState> = searchByEmailResult

    private val userAction: MutableLiveData<UserActionState> = MutableLiveData()
    val actionResult: LiveData<UserActionState> = userAction

    fun onSearch(email: String) = searchUserByEmail(email)

    fun onAddContact() {
        val result = searchResult.value as? SearchState.Success
        result?.user?.let {
            if (it is ContactProfileItem.UnknownContact) {
                requestContact(it)
            }
        }
    }

    fun onAcceptContact() {
        val result = searchResult.value as? SearchState.Success
        result?.user?.let {
            if (it is ContactProfileItem.RequestConfirmContact) {
                acceptContactForUser(it)
            }
        }
    }

    private fun acceptContactForUser(user: ContactProfileItem) {
        viewModelScope.launch {
            userAction.value = UserActionState.Loading

            val param = AcceptContact.Param(user.userId)

            when (val result = acceptContact(param)) {
                is AcceptContact.AcceptResult.Success -> {
                    userAction.value = UserActionState.Success(R.string.confirm_accept_success)
                    searchUserByEmail(user.email!!)
                }
                is AcceptContact.AcceptResult.Fail -> userAction.value =
                    UserActionState.Fail(result.error)
            }
        }
    }

    private fun requestContact(user: ContactProfileItem) {
        viewModelScope.launch {
            userAction.value = UserActionState.Loading

            val param = RequestContact.Param(loginManager.getCurrentUserId()!!, user.userId)
            if (requestContact(param)) {
                userAction.value = UserActionState.Success(R.string.request_sent)

                searchUserByEmail(user.email!!)
            }
        }
    }

    private fun searchUserByEmail(email: String) {
        viewModelScope.launch {
            searchByEmailResult.value = SearchState.Searching

            when (val result = searchUserByEmail(SearchUserByEmailParam(email))) {
                is SearchResult.Success -> onSearchSuccess(result.user)
                is SearchResult.Error -> searchByEmailResult.value = SearchState.Fail(result.error)
            }
        }
    }

    private suspend fun onSearchSuccess(user: User) {
        val param = UserContactsParam(loginManager.getCurrentUserId()!!)
        when (val retrieveContactsResult = retrieveUserContacts(param)) {
            is RetrieveContactResult.Success -> {
                val profileItem = mapToSearchUserProfile(retrieveContactsResult.contacts, user)
                searchByEmailResult.value = SearchState.Success(profileItem)
            }

            is RetrieveContactResult.Error -> searchByEmailResult.value =
                SearchState.Fail(retrieveContactsResult.error)
        }
    }

    private fun mapToSearchUserProfile(
        currentUserContacts: List<User.Contact>,
        user: User
    ): ContactProfileItem {
        val contactStatus =
            getUserContactStatusInRelationToCurrentUser(currentUserContacts, user.userId)
        return user.toSearchUserProfileItem(contactStatus?.status)
    }

    private fun getUserContactStatusInRelationToCurrentUser(
        currentUserContacts: List<User.Contact>,
        userId: String
    ) =
        currentUserContacts.find { it.userId == userId }
}