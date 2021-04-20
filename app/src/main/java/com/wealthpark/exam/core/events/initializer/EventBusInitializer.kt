package com.wealthpark.exam.core.events.initializer

import com.wealthpark.exam.WealthParkApplication
import com.wealthpark.exam.core.events.listeners.SynchronizeFoodsAndCitiesListener
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventBusInitializer @Inject constructor() {

    @Inject lateinit var eventBus: EventBus
    @Inject lateinit var synchronizeFoodsAndCitiesListener: SynchronizeFoodsAndCitiesListener

    fun initialize() {
        WealthParkApplication.appComponent.inject(this)
        eventBus.register(synchronizeFoodsAndCitiesListener)
    }
}