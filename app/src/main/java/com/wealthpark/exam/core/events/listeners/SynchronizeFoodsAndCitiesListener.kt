package com.wealthpark.exam.core.events.listeners

import com.wealthpark.exam.core.events.ApplicationResumedEvent
import com.wealthpark.exam.core.events.managers.FoodsAndCitiesSynchronizeManager
import com.wealthpark.exam.core.extensions.launch
import com.wealthpark.exam.core.extensions.logDebug
import com.wealthpark.exam.core.providers.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SynchronizeFoodsAndCitiesListener @Inject constructor(
    private val foodsAndCitiesSynchronizeManager: FoodsAndCitiesSynchronizeManager,
    private val coroutineContextProvider: CoroutineContextProvider
) {

    @Subscribe(threadMode = ThreadMode.ASYNC)
    fun onEvent(event: ApplicationResumedEvent) {
        GlobalScope.launch(
            coroutineContextProvider = coroutineContextProvider,
            work = {
                foodsAndCitiesSynchronizeManager.synchronize()
            },
            onSuccess = {
                logDebug("foods and cities synchronized.")
            },
            onFailure = {
                it.printStackTrace()
            }
        )
    }
}