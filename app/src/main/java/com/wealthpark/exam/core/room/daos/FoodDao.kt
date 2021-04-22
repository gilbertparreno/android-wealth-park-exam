package com.wealthpark.exam.core.room.daos

import androidx.room.Dao
import androidx.room.Query
import com.wealthpark.exam.core.room.base.BaseRoomDao
import com.wealthpark.exam.core.room.entities.Food

@Dao
interface FoodDao : BaseRoomDao<Food> {

    @Query("SELECT * FROM food")
    suspend fun findAll(): List<Food>

    @Query("SELECT * FROM food WHERE id = :id")
    suspend fun find(id: Int): Food?

    @Query("SELECT * FROM food WHERE name = :name")
    suspend fun getFoodByName(name: String): Food?
}