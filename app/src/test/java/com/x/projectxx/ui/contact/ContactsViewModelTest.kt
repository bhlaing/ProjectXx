package com.x.projectxx.ui.contact

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.x.projectxx.MockitoHelper
import com.x.projectxx.domain.user.model.ContactDetails
import com.x.projectxx.domain.user.GetContactDetails
import com.x.projectxx.domain.user.GetContactDetails.*
import com.x.projectxx.domain.user.GetCurrentUser
import com.x.projectxx.domain.user.GetCurrentUser.UserProfileResult
import com.x.projectxx.domain.user.model.User
import com.x.projectxx.getOrAwaitValue
import com.x.projectxx.ui.contacts.ContactsViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeoutException
import org.mockito.Mockito.`when` as whenever

class ContactsViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getCurrentUser: GetCurrentUser

    @Mock
    lateinit var getContactDetails: GetContactDetails

    @Mock
    lateinit var userProfileSuccess: UserProfileResult.Success

    lateinit var contactsViewModel: ContactsViewModel

    private val mockUser = User(
        "",
        "aa",
        "",
        "",
        contacts = listOf(User.Contact("1111"))
    )

    private val mockContactDetails =
        ContactDetails(
            "",
            "aa",
            "Just do it!",
            "imageurl"
        )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        contactsViewModel = ContactsViewModel(getCurrentUser, getContactDetails)
    }

    @Test
    fun `when current user has contacts then display contacts`() {
        runBlocking {
            whenever(getCurrentUser.invoke(Unit)).thenReturn(UserProfileResult.Success(mockUser))
            whenever(getContactDetails.invoke(MockitoHelper.anyObject())).thenReturn(
                ContactDetailsResult.Success(mockContactDetails)
            )

            contactsViewModel = ContactsViewModel(getCurrentUser, getContactDetails)

            with(contactsViewModel.contactList.getOrAwaitValue().first()) {
                assertEquals("aa", displayName)
                assertEquals("Just do it!", status)
                assertEquals("imageurl", profileImage)
            }
        }
    }

    @Test(expected = TimeoutException::class)
    fun `when getting current user fails then does not display contacts`() {
        runBlocking {
            whenever(getCurrentUser.invoke(Unit)).thenReturn(UserProfileResult.Error(-1))
            whenever(getContactDetails.invoke(MockitoHelper.anyObject())).thenReturn(
                ContactDetailsResult.Success(mockContactDetails)
            )

            contactsViewModel = ContactsViewModel(getCurrentUser, getContactDetails)

            contactsViewModel.contactList.getOrAwaitValue()
        }
    }

    @Test(expected = TimeoutException::class)
    fun `when getting contact detail fails then does not display contacts`() {
        runBlocking {
            whenever(getCurrentUser.invoke(Unit)).thenReturn(UserProfileResult.Error(-1))
            whenever(getContactDetails.invoke(MockitoHelper.anyObject())).thenReturn(
                ContactDetailsResult.Error(-1)
            )

            contactsViewModel = ContactsViewModel(getCurrentUser, getContactDetails)

            contactsViewModel.contactList.getOrAwaitValue()
        }
    }
}
