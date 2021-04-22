package com.wealthpark.exam.main.factories

import com.wealthpark.exam.R
import com.wealthpark.exam.core.room.entities.City
import com.wealthpark.exam.core.room.entities.Food
import com.wealthpark.exam.main.entities.MainListData

typealias FoodLocal = Food
typealias CityLocal = City

object MainFactory {
    fun createFoodAndCitiesFromLocal(
        foods: List<FoodLocal>,
        cities: List<CityLocal>
    ) = mutableListOf<MainListData>().also {
        val cityList = cities.map {
            MainListData.CityListItem(
                it.id,
                it.name,
                it.imageUrl,
                it.description
            )
        }
        if (cityList.isNotEmpty()) {
            it.add(MainListData.ListHeader(R.string.main_cities_header_label))
        }
        it.addAll(cityList)

        val foodList = foods.map {
            MainListData.FoodListItem(
                it.id,
                it.name,
                it.imageUrl
            )
        }
        if (foodList.isNotEmpty()) {
            it.add(MainListData.ListHeader(R.string.main_popular_food_header_label))
        }
        it.addAll(foodList)
    }
}