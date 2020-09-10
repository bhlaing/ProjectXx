package com.x.projectxx.ui.contact

import com.x.projectxx.BaseCoroutineTest
import com.x.projectxx.MockitoHelper
import com.x.projectxx.application.authentication.LoginManager
import com.x.projectxx.domain.contact.ObserveContactList
import com.x.projectxx.domain.contact.model.ContactDetails
import com.x.projectxx.domain.exceptions.GenericException
import com.x.projectxx.domain.user.model.ContactStatus
import com.x.projectxx.getOrAwaitValue
import com.x.projectxx.ui.home.contacts.ContactsViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock

class ContactsViewModelTest: BaseCoroutineTest() {
    @Mock
    lateinit var loginManager: LoginManager
    @Mock
    lateinit var observeContactList: ObserveContactList

    private lateinit var contactsViewModel: ContactsViewModel


    private val mockContactList =
        listOf(ContactDetails(
            "",
            "aa",
            "Just do it!",
            "imageurl",
            ContactStatus.PENDING
        ))

    @Before
    override fun setUp() {
      super.setUp()
        contactsViewModel = ContactsViewModel(observeContactList, loginManager)
    }

    @Test
    fun `when current user has contacts then display contacts`() {
        runBlocking {
            val mockFlow = flow {
                emit(mockContactList)
            }

            whenever(loginManager.getCurrentUserId()).thenReturn("")
            whenever(observeContactList.invoke(MockitoHelper.anyObject())).thenReturn(mockFlow)

            contactsViewModel = ContactsViewModel(observeContactList, loginManager)

            with(contactsViewModel.contactList.getOrAwaitValue().first()) {
                assertEquals("aa", displayName)
                assertEquals("Just do it!", status)
                assertEquals("imageurl", profileImage)
            }
        }
    }

    @Test
    fun `when getting contact detail fails then does not display contacts`() {
        runBlocking {
            whenever(loginManager.getCurrentUserId()).thenReturn("")

            // Work around because whenever(blahbalh).thenAnswer { GenericException() } doesn't work
            given(observeContactList.invoke(MockitoHelper.anyObject())).willAnswer { throw GenericException(-1) }

            contactsViewModel = ContactsViewModel(observeContactList, loginManager)
            val contactList = contactsViewModel.contactList.getOrAwaitValue()

            assertTrue(contactList.isEmpty())
        }
    }
}
