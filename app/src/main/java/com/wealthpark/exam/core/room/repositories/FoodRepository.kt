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

    override suspend fun findAll() = foodDao.findAll()

    override suspend fun find(id: Int) = foodDao.find(id)

    suspend fun getFoodByName(name: String) = foodDao.getFoodByName(name)
}