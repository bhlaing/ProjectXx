package com.x.projectxx.data.content

import com.x.projectxx.data.content.model.AddContentRequest
import com.x.projectxx.data.content.model.Content
import com.x.projectxx.data.content.model.ContentWrapperDO
import kotlinx.coroutines.flow.Flow

interface ContentRepository {
 fun observeUserContents(userId: String): Flow<List<ContentWrapperDO>>
 fun addUserContent(request: AddContentRequest): Boolean
}