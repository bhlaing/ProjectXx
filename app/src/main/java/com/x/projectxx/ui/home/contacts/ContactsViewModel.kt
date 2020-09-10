package com.x.projectxx.ui.home.contacts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.x.projectxx.application.authentication.LoginManager
import com.x.projectxx.application.extensions.observeAsLiveData
import com.x.projectxx.domain.contact.ObserveContactList
import com.x.projectxx.domain.contact.model.ContactDetails
import com.x.projectxx.domain.exceptions.GenericException
import com.x.projectxx.ui.home.contacts.model.ActionState
import kotlinx.coroutines.flow.collect

class ContactsViewModel @ViewModelInject constructor(
    private val observeContactList: ObserveContactList,
    private val loginManager: LoginManager
) : ViewModel() {
    private val observeActionState: MutableLiveData<ActionState> = MutableLiveData()

    val actionState = observeActionState.observeAsLiveData()
    val contactList = liveData {
            try {
                observeActionState.value = ActionState.Loading

                observeContactList(ObserveContactList.Param(loginManager.getCurrentUserId()!!))
                    .collect {
                        observeActionState.value = ActionState.Success()
                        emit(it)
                    }
            } catch (ex: GenericException) {
                emit(emptyList<ContactDetails>())
                observeActionState.value = ActionState.Fail(ex.errorMessage)
            }
        }
}
