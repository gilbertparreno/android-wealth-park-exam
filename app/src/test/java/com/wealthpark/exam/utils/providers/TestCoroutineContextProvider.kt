package com.wealthpark.exam.utils.providers

import com.wealthpark.exam.utils.TestCoroutineRule
import com.wealthpark.exam.core.providers.CoroutineContextProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class TestCoroutineContextProvider(testCoroutineRule: TestCoroutineRule) : CoroutineContextProvider() {
    override var Main: CoroutineContext = testCoroutineRule.testCoroutineDispatcher
    override var IO: CoroutineContext = testCoroutineRule.testCoroutineDispatcher
}