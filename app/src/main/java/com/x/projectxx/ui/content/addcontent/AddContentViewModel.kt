package com.x.projectxx.ui.content.addcontent

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.x.projectxx.application.authentication.LoginManager
import com.x.projectxx.data.content.ContentRepository
import com.x.projectxx.data.content.model.AddContentRequest
import com.x.projectxx.data.content.model.Content
import com.x.projectxx.data.content.model.ContentType
import com.x.projectxx.data.content.model.SecurityLevel
import com.x.projectxx.domain.content.ObserveContents
import kotlinx.coroutines.flow.collect
import java.util.*

class AddContentViewModel @ViewModelInject constructor() : ViewModel()