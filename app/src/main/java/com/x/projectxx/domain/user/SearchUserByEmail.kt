package com.x.projectxx.domain.user

import androidx.annotation.StringRes
import com.x.projectxx.R
import com.x.projectxx.data.identity.IdentityRepository
import com.x.projectxx.domain.shared.RetrieveResultInteractor
import com.x.projectxx.domain.user.SearchUserByEmail.*
import com.x.projectxx.domain.user.mappers.toUser
import com.x.projectxx.domain.user.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class SearchUserByEmail @Inject constructor(private val identityRepository: IdentityRepository) :
    RetrieveResultInteractor<Param, SearchResult>() {

    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun doWork(params: Param) = identityRepository.getUserProfileByEmail(params.email)?.let {
        SearchResult.Success(it.toUser())
    } ?: SearchResult.Error(R.string.no_matching_profile)


    class Param(val email: String)

    sealed class SearchResult {
        class Success(val user: User) : SearchResult()
        class Error(@StringRes val error: Int) : SearchResult()
    }
}