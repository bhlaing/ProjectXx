package com.x.projectxx.application.authentication

import com.google.firebase.auth.FirebaseUser
import com.x.projectxx.BaseTest
import com.x.projectxx.application.extensions.toAndroidUri
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito

class FirebaseUserMapperTest: BaseTest() {
    @Test
    fun `maps from firebase user to auth user correctly` () {
        val mockFireBaseUser = Mockito.mock(FirebaseUser::class.java)

        whenever(mockFireBaseUser.displayName).thenReturn(null)
        whenever(mockFireBaseUser.photoUrl).thenReturn("google.com".toAndroidUri())
        whenever(mockFireBaseUser.uid).thenReturn("uid")

        val userProfile = mockFireBaseUser.toUserProfile()

        with(userProfile) {
            assertEquals("", displayName)
            assertEquals("google.com".toAndroidUri(), image)
            assertEquals("uid", userId)
        }
    }
}