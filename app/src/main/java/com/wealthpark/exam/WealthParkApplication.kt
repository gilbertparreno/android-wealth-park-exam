package com.wealthpark.exam

import android.app.Application
import com.wealthpark.exam.core.callbacks.AppActivityCallbacks
import com.wealthpark.exam.core.di.AppComponent
import com.wealthpark.exam.core.di.AppModule
import com.wealthpark.exam.core.di.DaggerAppComponent
import com.wealthpark.exam.core.events.initializer.EventBusInitializer
import com.wealthpark.exam.core.networking.di.NetworkModule
import com.wealthpark.exam.core.room.di.RoomModule
import timber.log.Timber
import javax.inject.Inject

class WealthParkApplication : Application() {

    @Inject lateinit var eventBusInitializer: EventBusInitializer
    @Inject lateinit var appActivityCallbacks: AppActivityCallbacks

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .roomModule(RoomModule(this))
            .build()
            .also {
                it.inject(this)
            }

        eventBusInitializer.initialize()

        registerActivityLifecycleCallbacks(appActivityCallbacks)
    }
}