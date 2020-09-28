package com.x.projectxx.domain.content

import com.x.contentlibrary.ContentRepository
import com.x.contentlibrary.domain.SecurityLevel
import com.x.contentlibrary.request.TextContentRequest
import com.x.firebasecore.domain.result.SimpleResult
import com.x.projectxx.R
import com.x.projectxx.domain.shared.ResultInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class AddTextContent @Inject constructor(private val contentRepository: ContentRepository) :
    ResultInteractor<AddTextContent.Param, AddTextContent.AddResult>() {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun doWork(params: Param): AddResult =
        contentRepository.saveTextContent(
            params.userId,
            TextContentRequest(params.text, params.name, params.description, params.securityLevel)
        ).let {
            when (it) {
                is SimpleResult.Success -> AddResult.Success
                is SimpleResult.Fail -> AddResult.Fail(R.string.content_error_message)
            }
        }

    class Param(
        val userId: String,
        val name: String?,
        val description: String?,
        val text: String,
        val securityLevel: SecurityLevel
    )

    sealed class AddResult {
        object Success : AddResult()
        class Fail(errorMessage: Int) : AddResult()
    }
}