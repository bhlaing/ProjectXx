package com.x.contentlibrary

import com.x.contentlibrary.domain.UserContent
import com.x.contentlibrary.request.TextContentRequest
import com.x.firebasecore.domain.result.SimpleResult
import kotlinx.coroutines.flow.Flow

interface ContentRepository {
    suspend fun saveTextContent(userId: String, request: TextContentRequest): SimpleResult
    fun observeUserContents(userId: String): Flow<List<UserContent>>
}