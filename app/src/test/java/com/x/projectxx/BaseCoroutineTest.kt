package com.x.projectxx

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

open class BaseCoroutineTest: BaseTest() {
    @get:Rule
    val rule = InstantTaskExecutorRule()

}