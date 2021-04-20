package com.wealthpark.exam.core.room.daos

import androidx.room.*
import com.wealthpark.exam.core.room.base.BaseRoomDao
import com.wealthpark.exam.core.room.entities.Food

@Dao
interface FoodDao : BaseRoomDao<Food> {

    @Query("SELECT * FROM food")
    override suspend fun findAll(): List<Food>

    @Query("SELECT * FROM food WHERE id = :id")
    override suspend fun find(id: Int): Food?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertFoods(vararg foods: Food)

    @Query("SELECT * FROM food WHERE name = :name")
    suspend fun getFoodByName(name: String): Food?

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateFoods(vararg foods: Food)
}