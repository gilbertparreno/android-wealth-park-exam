package com.wealthpark.exam.core.room.di

import android.content.Context
import androidx.room.Room
import com.wealthpark.exam.core.room.daos.CityDao
import com.wealthpark.exam.core.room.daos.FoodDao
import com.wealthpark.exam.core.room.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(
    private val context: Context
) {

    @Provides
    @Singleton
    fun provideRoomDatabase(): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app-db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFoodDao(appDatabase: AppDatabase): FoodDao {
        return appDatabase.foodDao()
    }

    @Provides
    @Singleton
    fun provideCityDao(appDatabase: AppDatabase): CityDao {
        return appDatabase.cityDao()
    }
}