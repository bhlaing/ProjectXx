package com.x.projectxx.domain.content

import com.x.contentlibrary.ContentRepository
import com.x.contentlibrary.domain.UserContent
import com.x.projectxx.domain.shared.ResultInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveContents @Inject constructor(private val contentRepository: ContentRepository) :
    ResultInteractor<ObserveContents.Param, Flow<List<UserContent>>>() {

    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun doWork(params: Param): Flow<List<UserContent>> =
        contentRepository.observeUserContents(params.userId)

    class Param(val userId: String)
}