package com.x.projectxx.ui.content.addcontent

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x.projectxx.application.authentication.LoginManager
import com.x.projectxx.domain.content.AddTextContent
import kotlinx.coroutines.launch

class AddContentViewModel @ViewModelInject constructor(val addTextContent: AddTextContent,
val loginManager: LoginManager) : ViewModel() {

    init {


    }

    fun test() {
        viewModelScope.launch {
            AddTextContent.Param(loginManager.getCurrentUserId()!!,
                "Billy",
                "Some description",
                "Text content",
                com.x.contentlibrary.domain.SecurityLevel.DEFAULT
            ).run {
                addTextContent(this)
            }
        }
    }
}