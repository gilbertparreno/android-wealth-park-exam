package com.wealthpark.exam.core.networking.repositories

import com.wealthpark.exam.core.networking.services.AppService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    private val appService: AppService
) {
    suspend fun getFoodsAndCities() = appService.getFoodsAndCities()
}