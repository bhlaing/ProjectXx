package com.x.projectxx.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseUser
import com.x.projectxx.global.extensions.Event
import com.x.projectxx.global.login.LoginManager
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

    @Mock
    lateinit var loginManager: LoginManager

    @Mock
    lateinit var mockFireBaseUser: FirebaseUser

    @Mock
    lateinit var mockAccessToken: AccessToken

    lateinit var loginViewModel: LoginViewModel

    @Mock
    lateinit var observer: Observer<Event<LoginManager.AuthState>>


    @Captor
    lateinit var captor: ArgumentCaptor<Event<LoginManager.AuthState>>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        whenever(loginManager.getUserLoginStatus()).then {
            MutableLiveData(
                LoginManager.AuthState.LoggedIn(
                    mockFireBaseUser
                )
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
                    LoginManager.AuthState.LoggedIn(
                        mockFireBaseUser
                    )
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

            val logedOutState = captor.value.peekContent() as LoginManager.AuthState.LoggedOut

            assertEquals(logedOutState.error , "Some error")
        }
    }

    @Test
    fun `when user is successfully logged in from facebook, login call to LoginManager is made` () {
        loginViewModel.onFacebookLoginSuccess(mockAccessToken)

        verify(loginManager).loginWithFacebookToken(mockAccessToken)
    }
}