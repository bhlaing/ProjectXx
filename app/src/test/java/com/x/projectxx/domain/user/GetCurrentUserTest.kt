package com.x.projectxx.domain.user


import com.x.projectxx.R
import com.x.projectxx.application.authentication.LoginManager
import com.x.projectxx.data.identity.IdentityRepository
import com.x.projectxx.data.identity.model.UserProfile
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as whenever
import com.x.projectxx.domain.user.GetCurrentUser.UserProfileResult
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking

class GetCurrentUserTest {
    @Mock
    lateinit var loginManager: LoginManager
    @Mock
    lateinit var getCurrentUser: GetCurrentUser
    @Mock
    lateinit var identityRepository: IdentityRepository

    private val mockUserProfile = UserProfile(
        displayName = "ProjectXx",
        image = "google.com",
        userId = "uid",
        email = "a@b.c"
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        getCurrentUser = GetCurrentUser(loginManager, identityRepository)
    }

    @Test
    fun `when there is valid user logged in currently then return success result`() {
        runBlocking {
            whenever(loginManager.getCurrentUserId()).thenReturn("uid")
            whenever(identityRepository.getUserProfile("uid")).thenReturn(mockUserProfile)

            val profileResult = getCurrentUser(Unit)
            assertTrue(profileResult is UserProfileResult.Success)

            with(profileResult as UserProfileResult.Success) {
                assertEquals("ProjectXx", user.displayName)
                assertEquals("google.com", user.image)
                assertEquals("uid", user.userId)
                assertEquals("a@b.c", user.email)
            }
        }
    }

    @Test
    fun `when there is no valid user logged in then return error result`() {
        runBlocking {
            whenever(loginManager.getCurrentUserId()).thenReturn(null)

            val profileResult = getCurrentUser(Unit)
            assertTrue(profileResult is UserProfileResult.Error)

            with(profileResult as UserProfileResult.Error) {
                assertEquals(R.string.generic_error_message, error)
            }
        }
    }
}