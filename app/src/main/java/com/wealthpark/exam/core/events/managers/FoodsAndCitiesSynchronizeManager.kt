package com.wealthpark.exam.core.events.managers

import com.wealthpark.exam.core.events.FoodsAndCitiesSynchronizationEvent
import com.wealthpark.exam.core.networking.entities.FoodsAndCitiesApi
import com.wealthpark.exam.core.networking.repositories.AppRepository
import com.wealthpark.exam.core.room.entities.City
import com.wealthpark.exam.core.room.entities.Food
import com.wealthpark.exam.core.room.repositories.CityRepository
import com.wealthpark.exam.core.room.repositories.FoodRepository
import org.greenrobot.eventbus.EventBus
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

    suspend fun synchronize(postSyncEvent: Boolean = true) {
        appRepository.getFoodsAndCities().also {
            synchronizeFoods(it.foods)
            synchronizeCities(it.cities)
        }
        if (postSyncEvent) {
            eventBus.postSticky(FoodsAndCitiesSynchronizationEvent(true))
        }
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
        foodRepository.update(*forUpdate.toTypedArray())
        foodRepository.insert(*forInsert.toTypedArray())
        val forDeletion = foodRepository.findAll()
            .filter { localFood ->
                foodsApi.firstOrNull {
                    it.name == localFood.name && it.imageUrl == localFood.imageUrl
                } == null
            }
        foodRepository.delete(*forDeletion.toTypedArray())
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
        cityRepository.update(*forUpdate.toTypedArray())
        cityRepository.insert(*forInsert.toTypedArray())
        val forDeletion = cityRepository.findAll()
            .filter { localCity ->
                citiesAPi.firstOrNull {
                    it.name == localCity.name && it.imageUrl == localCity.imageUrl && it.description == localCity.description
                } == null
            }
        cityRepository.delete(*forDeletion.toTypedArray())
    }
}