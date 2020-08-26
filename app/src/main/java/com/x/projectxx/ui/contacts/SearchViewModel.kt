package com.x.projectxx.ui.contacts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.x.projectxx.application.authentication.LoginManager
import com.x.projectxx.domain.contact.RetrieveUserContacts
import com.x.projectxx.domain.contact.RetrieveUserContacts.*
import com.x.projectxx.domain.contact.RetrieveUserContacts.Param as UserContactsParam
import com.x.projectxx.domain.user.SearchUserByEmail
import com.x.projectxx.domain.user.SearchUserByEmail.Param as SearchUserByEmailParam
import com.x.projectxx.domain.user.SearchUserByEmail.*
import com.x.projectxx.domain.user.model.User
import com.x.projectxx.ui.contacts.model.SearchState
import com.x.projectxx.ui.contacts.model.ContactProfileItem
import com.x.projectxx.ui.contacts.model.toSearchUserProfileItem
import kotlinx.coroutines.launch
import com.x.projectxx.domain.contact.RequestContact as RequestContact

class SearchViewModel @ViewModelInject constructor(
    private val searchUserByEmail: SearchUserByEmail,
    private val requestContact: RequestContact,
    private val loginManager: LoginManager,
    private val retrieveUserContacts: RetrieveUserContacts
) : ViewModel() {

    private val searchByEmailResult: MutableLiveData<SearchState> = MutableLiveData()

    val searchResult: LiveData<SearchState> = searchByEmailResult

    fun onSearch(email: String) = searchUserByEmail(email)

    fun onStatusAction() {
        val result = searchResult.value as? SearchState.Success
        result?.user?.let {
            if (it is ContactProfileItem.UnknownContact) {
                requestContact(it)
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

    private fun requestContact(user: ContactProfileItem) {
        viewModelScope.launch {
            val param = RequestContact.Param(loginManager.getCurrentUserId()!!, user.userId)
            if(requestContact(param)) {
                searchUserByEmail(user.email!!)
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

    private fun mapToSearchUserProfile(currentUserContacts: List<User.Contact>, user: User): ContactProfileItem {
        val contactStatus = getUserContactStatusInRelationToCurrentUser(currentUserContacts, user.userId)
        return user.toSearchUserProfileItem(contactStatus?.status)
    }

    private fun getUserContactStatusInRelationToCurrentUser(
        currentUserContacts: List<User.Contact>,
        userId: String
    ) =
        currentUserContacts.find { it.userId == userId }
}