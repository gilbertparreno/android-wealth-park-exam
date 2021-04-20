package com.wealthpark.exam.core.room.repositories

import com.wealthpark.exam.core.room.base.BaseRoomRepository
import com.wealthpark.exam.core.room.daos.FoodDao
import com.wealthpark.exam.core.room.entities.Food
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodRepository @Inject constructor(
    private val foodDao: FoodDao
) : BaseRoomRepository<Food, FoodDao>(foodDao) {
    suspend fun insertFoods(vararg foods: Food) = foodDao.insertFoods(*foods)
    suspend fun getFoodByName(name: String) = foodDao.getFoodByName(name)
    suspend fun updateFoods(vararg foods: Food) = foodDao.updateFoods(*foods)
}