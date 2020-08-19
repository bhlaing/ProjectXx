package com.x.projectxx.ui.login

import com.x.projectxx.BaseTest
import com.x.projectxx.application.authentication.LoginManager
import com.x.projectxx.domain.user.model.User
import com.x.projectxx.ui.login.model.LoginState
import com.x.projectxx.ui.login.model.toLoginState
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.mockito.Mock

class LoginStateMapperTest: BaseTest() {
    @Mock
    lateinit var mockUser: User


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