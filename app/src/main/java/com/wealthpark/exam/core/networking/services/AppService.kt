package com.wealthpark.exam.core.networking.services

import com.wealthpark.exam.core.networking.entities.FoodsAndCitiesApi
import retrofit2.http.GET

interface AppService {

    @GET("a2b63ef226c08553b2f9")
    suspend fun getFoodsAndCities(): FoodsAndCitiesApi
}