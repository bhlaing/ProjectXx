package com.x.projectxx.ui.contacts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.x.projectxx.application.authentication.LoginManager
import com.x.projectxx.domain.user.SearchUserByEmail
import com.x.projectxx.domain.user.SearchUserByEmail.*
import com.x.projectxx.domain.user.model.User
import com.x.projectxx.ui.contacts.model.SearchState
import kotlinx.coroutines.launch
import com.x.projectxx.domain.contact.RequestContact as RequestContact

class SearchViewModel @ViewModelInject constructor(
    private val searchUserByEmail: SearchUserByEmail,
    private val requestContact: RequestContact,
    private val loginManager: LoginManager
) : ViewModel() {

    private val searchByEmailResult: MutableLiveData<SearchState> = MutableLiveData()

    val searchResult: LiveData<SearchState> = searchByEmailResult
    var user: User? = null

    fun onSearch(email: String) {
        viewModelScope.launch {
            searchByEmailResult.value = SearchState.Searching

            when (val result = searchUserByEmail(Param(email))) {
                is SearchResult.Success -> {
                    user = result.user
                    searchByEmailResult.value = SearchState.Success(user!!)
                }
                is SearchResult.Error -> searchByEmailResult.value = SearchState.Fail(result.error)
            }
        }
    }

    fun onAddContact() {
        viewModelScope.launch {
            val result = searchResult.value as? SearchState.Success
            result?.user?.let {
                val param = RequestContact.Param(loginManager.getCurrentUserId()!!, it.userId)
                requestContact(param)
            }
        }
    }
}