package com.x.projectxx.ui.search

import com.x.projectxx.BaseCoroutineTest
import com.x.projectxx.MockitoHelper
import com.x.projectxx.domain.user.SearchUserByEmail
import com.x.projectxx.domain.user.SearchUserByEmail.*
import com.x.projectxx.domain.user.model.User
import com.x.projectxx.ui.contacts.SearchViewModel
import com.x.projectxx.ui.contacts.model.SearchState
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class SearchViewModelTest: BaseCoroutineTest() {

    lateinit var viewModel: SearchViewModel

    @Mock
    lateinit var searchUserByEmail: SearchUserByEmail

    private val mockUser = User("aaa", "dd", "image", "email")

    @Before
    override fun setUp() {
        super.setUp()

        viewModel = SearchViewModel(searchUserByEmail)
    }

    @Test
    fun `when search is successful, then displays user`() {
        runBlocking {
            whenever(searchUserByEmail.invoke(MockitoHelper.anyObject())).then { SearchResult.Success(mockUser) }

            viewModel.onSearch("some@some.com")

            assertTrue(viewModel.searchResult.value is SearchState.Success)

            with(viewModel.searchResult.value as SearchState.Success) {
                assertEquals("aaa", user.userId)
                assertEquals("dd", user.displayName)
                assertEquals("image", user.image)
                assertEquals("email", user.email)
            }
        }
    }

    @Test
    fun `when search fails, then displays error message`() {
        runBlocking {
            whenever(searchUserByEmail.invoke(MockitoHelper.anyObject())).then { SearchResult.Error(-1) }

            viewModel.onSearch("some@some.com")

            assertTrue(viewModel.searchResult.value is SearchState.Fail)

            with(viewModel.searchResult.value as SearchState.Fail) {
                assertEquals(-1, error)
            }
        }
    }
}