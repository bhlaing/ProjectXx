package com.x.projectxx.ui.contacts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.x.projectxx.domain.user.SearchUserByEmail
import com.x.projectxx.domain.user.SearchUserByEmail.*
import com.x.projectxx.ui.contacts.model.SearchState
import com.x.projectxx.ui.login.model.LoginState
import kotlinx.coroutines.launch

class SearchViewModel @ViewModelInject constructor(private val searchUserByEmail: SearchUserByEmail) : ViewModel() {

    private val searchByEmailResult: MutableLiveData<SearchState> = MutableLiveData()

    val searchResult: LiveData<SearchState> = searchByEmailResult

    fun onSearch(email: String) {
        viewModelScope.launch {
            searchByEmailResult.postValue(SearchState.Searching)

            when(val result = searchUserByEmail(Param(email))) {
                is SearchResult.Success -> searchByEmailResult.postValue(SearchState.Success(result.user))
                is SearchResult.Error -> searchByEmailResult.postValue(SearchState.Fail(result.error))
            }
        }
    }
}