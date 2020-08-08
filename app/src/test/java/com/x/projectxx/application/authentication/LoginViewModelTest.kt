package com.x.projectxx.application.authentication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.facebook.AccessToken
import com.x.projectxx.application.extensions.Event
import com.x.projectxx.application.authentication.userprofile.UserProfile
import com.x.projectxx.ui.login.LoginViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.Mockito.`when` as whenever

class LoginViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock lateinit var loginManager: LoginManager
    private val mockUserProfile = UserProfile("", "", null)
    @Mock lateinit var mockAccessToken: AccessToken
    @Mock lateinit var observer: Observer<Event<LoginManager.AuthState>>
    @Captor lateinit var captor: ArgumentCaptor<Event<LoginManager.AuthState>>

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        whenever(loginManager.getUserLoginStatus()).then {
            MutableLiveData(
                LoginManager.AuthState.LoggedIn(mockUserProfile)
            )
        }
        loginViewModel = LoginViewModel(loginManager)

        loginViewModel.authState.observeForever(observer)
    }

    @Test
    fun `when user is logged in, auth state is updated to LoggedIn`() {
        runBlocking {
            clearInvocations(observer)
            whenever(loginManager.getUserLoginStatus()).then {
                MutableLiveData(
                    LoginManager.AuthState.LoggedIn(mockUserProfile)
                )
            }
            loginViewModel = LoginViewModel(loginManager)
            loginViewModel.authState.observeForever(observer)
            verify(observer).onChanged(captor.capture())

            //TO-DO: After creating user profile model we should assert the user too here
            assertTrue (captor.value.peekContent() is LoginManager.AuthState.LoggedIn)
        }
    }

    @Test
    fun `when user is logged out, auth state is updated to LoggedOut`() {
        runBlocking {
            clearInvocations(observer)
            whenever(loginManager.getUserLoginStatus()).then {
                MutableLiveData(
                    LoginManager.AuthState.LoggedOut("Some error")
                )
            }
            loginViewModel = LoginViewModel(loginManager)
            loginViewModel.authState.observeForever(observer)
            verify(observer).onChanged(captor.capture())

            //TO-DO: After creating user profile model we should assert the user too here
            assertTrue (captor.value.peekContent() is LoginManager.AuthState.LoggedOut)

            val loggedOutState = captor.value.peekContent() as LoginManager.AuthState.LoggedOut

            assertEquals(loggedOutState.error , "Some error")
        }
    }

    @Test
    fun `when user is successfully logged in from facebook, login call to LoginManager is made` () {
        loginViewModel.onFacebookLoginSuccess(mockAccessToken)

        verify(loginManager).loginWithFacebookToken(mockAccessToken)
    }
}