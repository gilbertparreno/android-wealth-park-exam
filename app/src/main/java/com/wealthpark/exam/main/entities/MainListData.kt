package com.wealthpark.exam.main.entities

import androidx.annotation.StringRes

sealed class MainListData {
    data class FoodListItem(
        val id: Int,
        val name: String,
        val imageUrl: String
    ) : MainListData()

    data class CityListItem(
        val id: Int,
        val name: String,
        val imageUrl: String,
        val description: String
    ) : MainListData()

    data class ListHeader(@StringRes val headerRes: Int) : MainListData()
}