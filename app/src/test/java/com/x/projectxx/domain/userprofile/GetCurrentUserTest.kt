package com.x.projectxx.domain.userprofile


import com.x.projectxx.R
import com.x.projectxx.application.authentication.LoginManager
import com.x.projectxx.domain.userprofile.model.User
import com.x.projectxx.domain.userprofile.usecase.UserProfileResult
import com.x.projectxx.domain.userprofile.usecase.GetCurrentUser
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as whenever

class GetCurrentUserTest {
    @Mock lateinit var loginManager: LoginManager
    @Mock lateinit var getCurrentUser: GetCurrentUser

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        getCurrentUser =
            GetCurrentUser(
                loginManager
            )

    }

    @Test
    fun `when there is valid user logged in currently then return success result` () {
        val mockUser = User(displayName = "ProjectXx",
            image = "google.com",
            userId = "uid",
                email = "a@b.c")

        whenever(loginManager.getCurrentUser()).thenReturn(mockUser)


        val profileResult  = getCurrentUser()
        assertTrue (profileResult is UserProfileResult.Success)

        with(profileResult as UserProfileResult.Success) {
            assertEquals("ProjectXx", user.displayName)
            assertEquals("google.com", user.image)
            assertEquals("uid", user.userId)
            assertEquals("a@b.c", user.email)
        }
    }

    @Test
    fun `when there is no valid user logged in then return error result` () {
        whenever(loginManager.getCurrentUser()).thenReturn(null)

        val profileResult  = getCurrentUser()
        assertTrue(profileResult is UserProfileResult.Error)

        with(profileResult as UserProfileResult.Error) {
            assertEquals(R.string.generic_error_message, error)
        }
    }
}