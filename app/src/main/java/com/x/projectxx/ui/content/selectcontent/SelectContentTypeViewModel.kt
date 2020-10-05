package com.x.projectxx.ui.content.selectcontent

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.x.contentlibrary.ContentRepository

class SelectContentTypeViewModel @ViewModelInject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {
    val contentsTypes = liveData {
        emit(contentRepository.getEligibleContentTypes())
    }
}