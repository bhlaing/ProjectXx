package com.x.projectxx.domain.user

import com.x.projectxx.BaseTest
import com.x.projectxx.R
import com.x.projectxx.data.identity.IdentityRepository
import com.x.projectxx.data.identity.model.UserProfile
import com.x.projectxx.domain.user.SearchUserByEmail.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class SearchUserByEmailTest : BaseTest() {

    lateinit var searchUserByEmail: SearchUserByEmail
    @Mock
    lateinit var identityRepository: IdentityRepository

    private val mockUserProfile = UserProfile(
        displayName = "ProjectXx",
        image = "google.com",
        userId = "uid",
        email = "a@b.c"
    )

    @Before
    override fun setUp() {
        super.setUp()

        searchUserByEmail = SearchUserByEmail(identityRepository)
    }

    @Test
    fun `when user profile exists then returns success result with user details`() {
        runBlocking {
            whenever(identityRepository.getUserProfileByEmail("email")).thenReturn(mockUserProfile)

            val result = searchUserByEmail(Param("email"))

            assertTrue(result is SearchResult.Success)

            with(result as SearchResult.Success) {
                assertEquals("uid", user.userId)
                assertEquals("ProjectXx", user.displayName)
                assertEquals("google.com", user.image)
                assertEquals("a@b.c", user.email)
            }
        }
    }


    @Test
    fun `when user profile doesn't exists then returns fail result with error message`() {
        runBlocking {
            whenever(identityRepository.getUserProfileByEmail("email")).thenReturn(null)

            val result = searchUserByEmail(Param("email"))

            assertTrue(result is SearchResult.Error)

            with(result as SearchResult.Error) {
                assertEquals(R.string.no_matching_profile, error)
            }
        }
    }
}