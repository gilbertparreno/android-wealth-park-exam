package com.wealthpark.exam.core.events.managers

import com.wealthpark.exam.core.events.FoodsAndCitiesSynchronizedEvent
import com.wealthpark.exam.core.networking.entities.FoodsAndCitiesApi
import com.wealthpark.exam.core.networking.repositories.AppRepository
import com.wealthpark.exam.core.room.entities.City
import com.wealthpark.exam.core.room.entities.Food
import com.wealthpark.exam.core.room.repositories.CityRepository
import com.wealthpark.exam.core.room.repositories.FoodRepository
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

typealias FoodApi = FoodsAndCitiesApi.Food
typealias CityApi = FoodsAndCitiesApi.City

@Singleton
class FoodsAndCitiesSynchronizeManager @Inject constructor(
    private val appRepository: AppRepository,
    private val eventBus: EventBus,
    private val foodRepository: FoodRepository,
    private val cityRepository: CityRepository
) {

    suspend fun synchronize() {
        appRepository.getFoodsAndCities().also {

            synchronizeFoods(it.foods)
            synchronizeCities(it.cities)
            val foods = foodRepository.findAll()
            val cities = cityRepository.findAll()
            Timber.d("")
        }
//        foodDao.insertFoods(*foodsAndCities.foods.toTypedArray())
        eventBus.postSticky(FoodsAndCitiesSynchronizedEvent())
    }

    private suspend fun synchronizeFoods(foodsApi: List<FoodApi>) {
        val forUpdate = mutableListOf<Food>()
        val forInsert = mutableListOf<Food>()
        foodsApi.forEach { foodApi ->
            val localFood = foodRepository.getFoodByName(foodApi.name)
            if (localFood != null) {
                forUpdate.add(localFood.copy(imageUrl = foodApi.imageUrl))
            } else {
                forInsert.add(
                    Food(name = foodApi.name, imageUrl = foodApi.imageUrl)
                )
            }
        }
        foodRepository.updateFoods(*forUpdate.toTypedArray())
        foodRepository.insertFoods(*forInsert.toTypedArray())
    }

    private suspend fun synchronizeCities(citiesAPi: List<CityApi>) {
        val forUpdate = mutableListOf<City>()
        val forInsert = mutableListOf<City>()
        citiesAPi.forEach { cityAPi ->
            val localCity = cityRepository.getCityByName(cityAPi.name)
            if (localCity != null) {
                forUpdate.add(
                    localCity.copy(
                        imageUrl = cityAPi.imageUrl,
                        description = cityAPi.description
                    )
                )
            } else {
                forInsert.add(
                    City(
                        name = cityAPi.name,
                        imageUrl = cityAPi.imageUrl,
                        description = cityAPi.description
                    )
                )
            }
        }
        cityRepository.updateCities(*forUpdate.toTypedArray())
        cityRepository.insertCities(*forInsert.toTypedArray())
    }
}