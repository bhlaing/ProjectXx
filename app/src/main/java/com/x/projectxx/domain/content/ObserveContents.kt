package com.x.projectxx.domain.content

import com.x.projectxx.data.content.ContentRepository
import com.x.projectxx.domain.content.model.UserContent
import com.x.projectxx.domain.shared.ResultInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveContents @Inject constructor(private val contentRepository: ContentRepository) :
    ResultInteractor<ObserveContents.Param, Flow<List<UserContent>>>() {

    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun doWork(params: Param): Flow<List<UserContent>> =
        contentRepository.observeUserContents(params.userId)
            .map {
                it.toUserContents()
            }

    class Param(val userId: String)
}