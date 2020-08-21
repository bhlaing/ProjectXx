package com.x.projectxx.ui.contacts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.x.projectxx.domain.user.SearchUserByEmail
import com.x.projectxx.domain.user.SearchUserByEmail.*
import com.x.projectxx.ui.contacts.model.SearchState
import kotlinx.coroutines.launch

class SearchViewModel @ViewModelInject constructor(private val searchUserByEmail: SearchUserByEmail) : ViewModel() {

    private val searchByEmailResult: MutableLiveData<SearchState> = MutableLiveData()

    val searchResult: LiveData<SearchState> = searchByEmailResult

    fun onSearch(email: String) {
        viewModelScope.launch {
            searchByEmailResult.value = SearchState.Searching

            when(val result = searchUserByEmail(Param(email))) {
                is SearchResult.Success -> searchByEmailResult.value = SearchState.Success(result.user)
                is SearchResult.Error -> searchByEmailResult.value = SearchState.Fail(result.error)
            }
        }
    }
}