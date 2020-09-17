package com.x.projectxx.ui.home.dashboard

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.x.projectxx.application.authentication.LoginManager
import com.x.projectxx.application.extensions.observeAsLiveData
import com.x.projectxx.domain.content.ObserveContents
import com.x.projectxx.ui.content.toUserContentItem
import com.x.projectxx.ui.home.contacts.model.ActionState
import kotlinx.coroutines.flow.collect
import java.lang.Exception

class DashboardViewModel @ViewModelInject constructor(
    private val observeContents: ObserveContents,
    private val loginManager: LoginManager
) : ViewModel() {
    private val observeActionState: MutableLiveData<ActionState> = MutableLiveData()

    val actionState = observeActionState.observeAsLiveData()

    val contents = liveData {

        try{
            ObserveContents.Param(loginManager.getCurrentUserId()!!)
                .run {
                    observeContents(this)
                        .collect {
                            emit(it.map { userContent -> userContent.toUserContentItem()  })
                        }
                }
        } catch (ex: Exception) {
            // exception
        }
    }
}