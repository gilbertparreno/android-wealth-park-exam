package com.wealthpark.exam.core.di

import com.wealthpark.exam.WealthParkApplication
import com.wealthpark.exam.core.events.initializer.EventBusInitializer
import com.wealthpark.exam.core.networking.di.NetworkModule
import com.wealthpark.exam.core.room.di.RoomModule
import com.wealthpark.exam.details.fragments.DetailsFragment
import com.wealthpark.exam.main.activities.MainActivity
import com.wealthpark.exam.main.fragments.MainFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, NetworkModule::class, RoomModule::class])
@Singleton
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: MainFragment)
    fun inject(detailsFragment: DetailsFragment)

    fun inject(wealthParkApplication: WealthParkApplication)
    fun inject(eventBusInitializer: EventBusInitializer)
}