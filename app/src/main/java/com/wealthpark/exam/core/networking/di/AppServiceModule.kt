package com.wealthpark.exam.core.networking.di

import com.wealthpark.exam.core.networking.services.AppService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AppServiceModule {

    @Provides
    @Singleton
    fun provideAppService(retrofit: Retrofit): AppService {
        return retrofit.create(AppService::class.java)
    }
}