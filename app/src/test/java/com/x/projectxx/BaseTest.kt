package com.x.projectxx
import androidx.annotation.CallSuper
import org.junit.Before
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

abstract class BaseTest {
    @Before
    @CallSuper
    open fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    fun <T> whenever(methodCall: T) = Mockito.`when`(methodCall)
}