package com.wealthpark.exam.core.networking.entities

import com.google.gson.annotations.SerializedName

data class FoodsAndCitiesApi(
    val foods: List<Food>,
    val cities: List<City>
) {
    data class Food(
        val name: String,
        @SerializedName("image") val imageUrl: String
    )

    data class City(
        val name: String,
        @SerializedName("image") val imageUrl: String,
        val description: String
    )
}