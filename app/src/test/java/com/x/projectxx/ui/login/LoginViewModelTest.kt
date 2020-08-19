package com.x.projectxx.ui.login

import com.facebook.AccessToken
import com.x.projectxx.BaseCoroutineTest
import com.x.projectxx.application.authentication.LoginManager
import com.x.projectxx.domain.user.model.User
import com.x.projectxx.ui.login.model.LoginState
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.*

class LoginViewModelTest: BaseCoroutineTest() {

    @Mock
    lateinit var loginManager: LoginManager
    private val mockUser = User("", "", null, "email")
    @Mock
    lateinit var mockAccessToken: AccessToken
    private lateinit var loginViewModel: LoginViewModel

    @Before
    override fun setUp() {
        super.setUp()
        loginViewModel = LoginViewModel(loginManager)

    }

    @Test
    fun `when user login is successful, then update authState to logged in`() {
        runBlocking {
            whenever(loginManager.loginWithFacebookToken(mockAccessToken)).thenReturn(
                LoginManager.AuthState.LoggedIn(
                    mockUser
                )
            )

            loginViewModel = LoginViewModel(loginManager)
            loginViewModel.onFacebookLoginSuccess(mockAccessToken)

            assertTrue(loginViewModel.authState.value?.peekContent() is LoginState.Success)
        }
    }

    @Test
    fun `when login failed, then update authState to logged out`() {
        runBlocking {
            whenever(loginManager.loginWithFacebookToken(mockAccessToken)).thenReturn(
                LoginManager.AuthState.LoggedOut(
                    "error"
                )
            )

            loginViewModel = LoginViewModel(loginManager)
            loginViewModel.onFacebookLoginSuccess(mockAccessToken)

            assertTrue(loginViewModel.authState.value?.peekContent() is LoginState.Failed)
        }
    }
}
