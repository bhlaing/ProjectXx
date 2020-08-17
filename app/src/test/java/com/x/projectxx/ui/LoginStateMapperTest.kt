package com.x.projectxx.ui

import com.x.projectxx.application.authentication.LoginManager
import com.x.projectxx.domain.userprofile.model.User
import com.x.projectxx.ui.login.model.LoginState
import com.x.projectxx.ui.login.model.toLoginState
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LoginStateMapperTest {
    @Mock
    lateinit var mockUser: User

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `maps auth state logged in to LoginState Success`() {
        val authSate = LoginManager.AuthState.LoggedIn(mockUser)

        assertTrue(authSate.toLoginState() is LoginState.Success)
    }


    @Test
    fun `maps auth state logged out to LoginState Failed`() {
        val loginState = LoginManager.AuthState.LoggedOut("Some error").toLoginState()

        assertTrue(loginState is LoginState.Failed)
        assertEquals("Some error", (loginState as LoginState.Failed).error)
    }
}