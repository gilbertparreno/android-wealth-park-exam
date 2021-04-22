package com.wealthpark.exam.main.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wealthpark.exam.core.events.managers.FoodsAndCitiesSynchronizeManager
import com.wealthpark.exam.core.extensions.SingleLiveEvent
import com.wealthpark.exam.core.extensions.launch
import com.wealthpark.exam.core.networking.taskStatus.TaskStatus
import com.wealthpark.exam.core.providers.CoroutineContextProvider
import com.wealthpark.exam.core.room.repositories.CityRepository
import com.wealthpark.exam.core.room.repositories.FoodRepository
import com.wealthpark.exam.main.entities.MainListData
import com.wealthpark.exam.main.factories.CityLocal
import com.wealthpark.exam.main.factories.FoodLocal
import com.wealthpark.exam.main.factories.MainFactory
import kotlinx.coroutines.delay
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val cityRepository: CityRepository,
    private val foodRepository: FoodRepository,
    private val coroutineContextProvider: CoroutineContextProvider,
    private val foodsAndCitiesSynchronizeManager: FoodsAndCitiesSynchronizeManager
) : ViewModel() {

    val statusEvent = SingleLiveEvent<TaskStatus<List<MainListData>>>()

    fun getLatestFoodsAndCities(ignoreUpdatesFromApi: Boolean = true) {
        viewModelScope.launch(
            coroutineContextProvider = coroutineContextProvider,
            work = {
                delay(250)
                if (!ignoreUpdatesFromApi) {
                    foodsAndCitiesSynchronizeManager.synchronize(false)
                }
                val foods = foodRepository.findAll()
                val cities = cityRepository.findAll()
                getMainDataFromLocal(foods, cities)
            },
            onSuccess = {
                statusEvent.value = TaskStatus.success(it)
            },
            onFailure = {
                statusEvent.value = TaskStatus.error(it)
            }
        )
    }

    private fun getMainDataFromLocal(
        foods: List<FoodLocal>,
        cities: List<CityLocal>
    ): List<MainListData> {
        return MainFactory.createFoodAndCitiesFromLocal(
            foods,
            cities
        )
    }
}