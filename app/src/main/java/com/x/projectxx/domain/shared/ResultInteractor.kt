package com.x.projectxx.domain.shared

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Base domain usecase for retrieving stuffs using coroutine
 */
abstract class ResultInteractor<in P, R> {
    abstract val dispatcher: CoroutineDispatcher

    suspend operator fun invoke(params: P): R {
        return withContext(dispatcher) { doWork(params) }
    }

    protected abstract suspend fun doWork(params: P): R
}
