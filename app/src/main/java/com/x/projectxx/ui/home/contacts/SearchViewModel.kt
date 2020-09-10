package com.x.projectxx.ui.home.contacts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.x.projectxx.application.authentication.LoginManager
import com.x.projectxx.domain.contact.AcceptContact
import com.x.projectxx.domain.contact.RetrieveUserContactsStatus
import com.x.projectxx.domain.contact.RetrieveUserContactsStatus.*
import com.x.projectxx.domain.contact.RetrieveUserContactsStatus.Param as UserContactsParam
import com.x.projectxx.domain.user.SearchUserByEmail
import com.x.projectxx.domain.user.SearchUserByEmail.Param as SearchUserByEmailParam
import com.x.projectxx.domain.user.SearchUserByEmail.*
import com.x.projectxx.domain.user.model.User
import com.x.projectxx.ui.home.contacts.model.SearchState
import com.x.projectxx.ui.home.contacts.model.SearchProfileItem
import com.x.projectxx.ui.home.contacts.model.ActionState
import com.x.projectxx.ui.home.contacts.model.toSearchUserProfileItem
import kotlinx.coroutines.launch
import com.x.projectxx.domain.contact.RequestContact as RequestContact
import com.x.projectxx.R
import com.x.projectxx.domain.contact.DeleteContact
import com.x.projectxx.domain.contact.DeleteContact.DeleteResult

class SearchViewModel @ViewModelInject constructor(
    private val searchUserByEmail: SearchUserByEmail,
    private val requestContact: RequestContact,
    private val loginManager: LoginManager,
    private val retrieveUserContactsStatus: RetrieveUserContactsStatus,
    private val acceptContact: AcceptContact,
    private val deleteContact: DeleteContact
) : ViewModel() {

    private val searchByEmailResult: MutableLiveData<SearchState> = MutableLiveData()
    val searchResult: LiveData<SearchState> = searchByEmailResult

    private val action: MutableLiveData<ActionState> = MutableLiveData()
    val actionResult: LiveData<ActionState> = action

    fun onSearch(email: String) = searchUserByEmail(email)

    fun onAddContact() {
        val result = searchResult.value as? SearchState.Success
        result?.user?.let {
            if (it is SearchProfileItem.Unknown) {
                requestContact(it)
            }
        }
    }

    fun onAcceptContact() {
        val result = searchResult.value as? SearchState.Success
        result?.user?.let {
            if (it is SearchProfileItem.RequestConfirm) {
                acceptContactForUser(it)
            }
        }
    }

    fun onCancelContact() {
        val result = searchResult.value as? SearchState.Success
        result?.user?.let {
            if (it is SearchProfileItem.Pending) {
                deleteUserContact(it)
            }
        }
    }

    private fun deleteUserContact(user: SearchProfileItem) {
        viewModelScope.launch {
            action.value = ActionState.Loading

            val param = DeleteContact.Param(loginManager.getCurrentUserId()!!, user.userId)

            when (val result = deleteContact(param)) {
                is DeleteResult.Success -> {
                    action.value = ActionState.Success(R.string.confirm_cancel_success)
                    searchUserByEmail(user.email!!)
                }
                is DeleteResult.Fail -> action.value =
                    ActionState.Fail(result.error)
            }
        }
    }

    private fun acceptContactForUser(user: SearchProfileItem) {
        viewModelScope.launch {
            action.value = ActionState.Loading

            val param = AcceptContact.Param(user.userId)

            when (val result = acceptContact(param)) {
                is AcceptContact.AcceptResult.Success -> {
                    action.value = ActionState.Success(R.string.confirm_accept_success)
                    searchUserByEmail(user.email!!)
                }
                is AcceptContact.AcceptResult.Fail -> action.value =
                    ActionState.Fail(result.error)
            }
        }
    }

    private fun requestContact(user: SearchProfileItem) {
        viewModelScope.launch {
            action.value = ActionState.Loading

            val param = RequestContact.Param(loginManager.getCurrentUserId()!!, user.userId)
            if (requestContact(param)) {
                action.value = ActionState.Success(R.string.request_sent)

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
        when (val retrieveContactsResult = retrieveUserContactsStatus(param)) {
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
    ): SearchProfileItem {
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