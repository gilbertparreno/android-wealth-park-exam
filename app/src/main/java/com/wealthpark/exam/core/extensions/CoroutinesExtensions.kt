package com.wealthpark.exam.core.extensions

import com.wealthpark.exam.core.providers.CoroutineContextProvider
import kotlinx.coroutines.*

fun <R> CoroutineScope.launch(
    coroutineContextProvider: CoroutineContextProvider,
    work: suspend CoroutineScope.() -> R,
    onSuccess: (R) -> Unit,
    onFailure: (error: Throwable) -> Unit
) {
    launch(coroutineContextProvider.IO) {
        runCatching {
            work()
        }.also { result ->
            launch(coroutineContextProvider.Main) {
                result.onSuccess {
                    onSuccess(it)
                }.onFailure {
                    onFailure(it)
                }
            }
        }
    }
}

fun <T> CoroutineScope.launch(work: suspend CoroutineScope.() -> T) {
    launch(Dispatchers.IO) {
        work()
    }
}