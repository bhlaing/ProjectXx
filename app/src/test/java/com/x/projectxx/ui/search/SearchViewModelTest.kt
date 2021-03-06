package com.x.projectxx.ui.search

import com.x.projectxx.BaseCoroutineTest
import com.x.projectxx.MockitoHelper
import com.x.projectxx.application.authentication.LoginManager
import com.x.projectxx.domain.contact.AcceptContact
import com.x.projectxx.domain.contact.AcceptContact.*
import com.x.projectxx.domain.contact.DeleteContact
import com.x.projectxx.domain.contact.DeleteContact.*
import com.x.projectxx.domain.contact.RequestContact
import com.x.projectxx.domain.contact.RetrieveUserContactsStatus
import com.x.projectxx.domain.contact.RetrieveUserContactsStatus.*
import com.x.projectxx.domain.user.SearchUserByEmail
import com.x.projectxx.domain.user.SearchUserByEmail.*
import com.x.projectxx.domain.user.model.ContactStatus
import com.x.projectxx.domain.user.model.User
import com.x.projectxx.ui.home.contacts.SearchViewModel
import com.x.projectxx.ui.home.contacts.model.SearchProfileItem
import com.x.projectxx.ui.home.contacts.model.SearchState
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class SearchViewModelTest : BaseCoroutineTest() {

    private lateinit var viewModel: SearchViewModel

    @Mock
    lateinit var requestContact: RequestContact

    @Mock
    lateinit var loginManager: LoginManager

    @Mock
    lateinit var retrieveUserContactsStatus: RetrieveUserContactsStatus

    @Mock
    lateinit var acceptContact: AcceptContact

    @Mock
    lateinit var searchUserByEmail: SearchUserByEmail

    @Mock
    lateinit var deleteContact: DeleteContact

    private val mockUser = User("aaa", "dd", "image", "email")

    @Before
    override fun setUp() {
        super.setUp()

        viewModel = SearchViewModel(
            searchUserByEmail,
            requestContact,
            loginManager,
            retrieveUserContactsStatus,
            acceptContact,
            deleteContact
        )
    }

    @Test
    fun `when search is successful and user profile matches pending status, then displays pending item`() {

        runBlocking {
            whenever(loginManager.getCurrentUserId()).then { "aaa" }
            whenever(searchUserByEmail.invoke(MockitoHelper.anyObject())).then {
                SearchResult.Success(
                    mockUser
                )
            }
            whenever(retrieveUserContactsStatus.invoke(MockitoHelper.anyObject())).then {
                RetrieveContactResult.Success(
                    listOf(User.Contact("aaa", ContactStatus.PENDING))
                )
            }


            viewModel.onSearch("some@some.com")

            assertTrue(viewModel.searchResult.value is SearchState.Success)

            with(viewModel.searchResult.value as SearchState.Success) {

                assertTrue(user is SearchProfileItem.Pending)
                assertEquals("aaa", user.userId)
                assertEquals("dd", user.displayName)
                assertEquals("image", user.image)
                assertEquals("email", user.email)

            }
        }
    }

    @Test
    fun `when search is successful and user profile matches request status, then displays request item`() {

        runBlocking {
            whenever(loginManager.getCurrentUserId()).then { "aaa" }
            whenever(searchUserByEmail.invoke(MockitoHelper.anyObject())).then {
                SearchResult.Success(
                    mockUser
                )
            }
            whenever(retrieveUserContactsStatus.invoke(MockitoHelper.anyObject())).then {
                RetrieveContactResult.Success(
                    listOf(User.Contact("aaa", ContactStatus.REQUEST))
                )
            }


            viewModel.onSearch("some@some.com")

            assertTrue(viewModel.searchResult.value is SearchState.Success)

            with(viewModel.searchResult.value as SearchState.Success) {

                assertTrue(user is SearchProfileItem.RequestConfirm)
                assertEquals("aaa", user.userId)
                assertEquals("dd", user.displayName)
                assertEquals("image", user.image)
                assertEquals("email", user.email)

            }
        }
    }

    @Test
    fun `when search is successful and user profile matches confirmed status, then displays confirmed item`() {

        runBlocking {
            whenever(loginManager.getCurrentUserId()).then { "aaa" }
            whenever(searchUserByEmail.invoke(MockitoHelper.anyObject())).then {
                SearchResult.Success(
                    mockUser
                )
            }
            whenever(retrieveUserContactsStatus.invoke(MockitoHelper.anyObject())).then {
                RetrieveContactResult.Success(
                    listOf(User.Contact("aaa", ContactStatus.CONFIRMED))
                )
            }

            viewModel.onSearch("some@some.com")

            assertTrue(viewModel.searchResult.value is SearchState.Success)

            with(viewModel.searchResult.value as SearchState.Success) {

                assertTrue(user is SearchProfileItem.Confirmed)
                assertEquals("aaa", user.userId)
                assertEquals("dd", user.displayName)
                assertEquals("image", user.image)
                assertEquals("email", user.email)

            }
        }
    }

    @Test
    fun `when search is successful and result is not a friend, then displays unknown item`() {

        runBlocking {
            whenever(loginManager.getCurrentUserId()).then { "aaa" }
            whenever(searchUserByEmail.invoke(MockitoHelper.anyObject())).then {
                SearchResult.Success(
                    mockUser
                )
            }
            whenever(retrieveUserContactsStatus.invoke(MockitoHelper.anyObject())).then {
                RetrieveContactResult.Success(
                    listOf(User.Contact("bbb", ContactStatus.CONFIRMED))
                )
            }

            viewModel.onSearch("some@some.com")

            assertTrue(viewModel.searchResult.value is SearchState.Success)

            with(viewModel.searchResult.value as SearchState.Success) {

                assertTrue(user is SearchProfileItem.Unknown)
                assertEquals("aaa", user.userId)
                assertEquals("dd", user.displayName)
                assertEquals("image", user.image)
                assertEquals("email", user.email)
            }
        }
    }

    @Test
    fun `if unable to fetch current user contacts then display error`() {
        runBlocking {
            whenever(loginManager.getCurrentUserId()).then { "aaa" }
            whenever(searchUserByEmail.invoke(MockitoHelper.anyObject())).then {
                SearchResult.Success(
                    mockUser
                )
            }
            whenever(retrieveUserContactsStatus.invoke(MockitoHelper.anyObject())).then {
                RetrieveContactResult.Error(
                    -1
                )
            }

            viewModel.onSearch("some@some.com")

            assertTrue(viewModel.searchResult.value is SearchState.Fail)

            with(viewModel.searchResult.value as SearchState.Fail) {
                assertEquals(-1, error)
            }
        }
    }

    @Test
    fun `when search fails, then displays error message`() {
        runBlocking {
            whenever(searchUserByEmail.invoke(MockitoHelper.anyObject())).then {
                SearchResult.Error(
                    -1
                )
            }

            viewModel.onSearch("some@some.com")

            assertTrue(viewModel.searchResult.value is SearchState.Fail)

            with(viewModel.searchResult.value as SearchState.Fail) {
                assertEquals(-1, error)
            }
        }
    }

    @Test
    fun `when accepting contact invoke accept contact`() {
        runBlocking {
            runBlocking {
                whenever(loginManager.getCurrentUserId()).then { "aaa" }
                whenever(searchUserByEmail.invoke(MockitoHelper.anyObject())).then {
                    SearchResult.Success(
                        mockUser
                    )
                }
                whenever(retrieveUserContactsStatus.invoke(MockitoHelper.anyObject())).then {
                    RetrieveContactResult.Success(
                        listOf(User.Contact("aaa", ContactStatus.REQUEST))
                    )
                }
                whenever(acceptContact.invoke(MockitoHelper.anyObject())).thenReturn(AcceptResult.Success)

                viewModel.onSearch("some@some.com")
                viewModel.onAcceptContact()


                assertTrue(viewModel.searchResult.value is SearchState.Success)
                verify(acceptContact).invoke(MockitoHelper.anyObject())

                verify(searchUserByEmail, times(2)).invoke(MockitoHelper.anyObject())
            }
        }
    }

    @Test
    fun `when cancelling contact request invoke delete contact`() {
        runBlocking {
            whenever(loginManager.getCurrentUserId()).then { "aaa" }
            whenever(searchUserByEmail.invoke(MockitoHelper.anyObject())).then {
                SearchResult.Success(
                    mockUser
                )
            }
            whenever(retrieveUserContactsStatus.invoke(MockitoHelper.anyObject())).then {
                RetrieveContactResult.Success(
                    listOf(User.Contact("aaa", ContactStatus.PENDING))
                )
            }
            whenever(deleteContact.invoke(MockitoHelper.anyObject())).thenReturn(DeleteResult.Success)

            viewModel.onSearch("some@some.com")
            viewModel.onCancelContact()


            assertTrue(viewModel.searchResult.value is SearchState.Success)
            verify(deleteContact).invoke(MockitoHelper.anyObject())

            verify(searchUserByEmail, times(2)).invoke(MockitoHelper.anyObject())
        }
    }
}