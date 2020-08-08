package com.x.projectxx.domain.userprofile


import com.google.firebase.auth.FirebaseUser
import com.x.projectxx.R
import com.x.projectxx.application.authentication.LoginManager
import com.x.projectxx.application.extensions.toAndroidUri
import com.x.projectxx.domain.userprofile.model.UserProfileResult
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as whenever

class GetCurrentUserTest {
    @Mock lateinit var loginManager: LoginManager
    @Mock lateinit var getCurrentUser: GetCurrentUser

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        getCurrentUser = GetCurrentUser(loginManager)

    }

    @Test
    fun `when there is valid user logged in currently then return success result` () {
        val mockFireBaseUser = Mockito.mock(FirebaseUser::class.java)

        whenever(mockFireBaseUser.displayName).thenReturn("ProjectXx")
        whenever(mockFireBaseUser.photoUrl).thenReturn("google.com".toAndroidUri())
        whenever(mockFireBaseUser.uid).thenReturn("uid")

        whenever(loginManager.getCurrentUser()).thenReturn(mockFireBaseUser)


        val profileResult  = getCurrentUser()
        assertTrue (profileResult is UserProfileResult.Success)

        with(profileResult as UserProfileResult.Success) {
            assertEquals("ProjectXx", userProfile.displayName)
            assertEquals("google.com".toAndroidUri(), userProfile.uri)
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