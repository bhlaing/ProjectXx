package com.x.projectxx.application.authentication

import com.google.firebase.auth.FirebaseUser
import com.x.projectxx.application.extensions.toAndroidUri
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito

class FirebaseUserMapperTest {
    @Test
    fun `maps from firebase user to auth user correctly` () {
        val mockFireBaseUser = Mockito.mock(FirebaseUser::class.java)

        Mockito.`when`(mockFireBaseUser.displayName).thenReturn(null)
        Mockito.`when`(mockFireBaseUser.photoUrl).thenReturn("google.com".toAndroidUri())
        Mockito.`when`(mockFireBaseUser.uid).thenReturn("uid")

        val userProfile = mockFireBaseUser.toUserProfile()

        with(userProfile) {
            assertEquals("", displayName)
            assertEquals("google.com".toAndroidUri(), image)
            assertEquals("uid", userId)
        }
    }
}