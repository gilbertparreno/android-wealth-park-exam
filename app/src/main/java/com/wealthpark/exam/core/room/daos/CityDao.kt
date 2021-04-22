package com.wealthpark.exam.core.room.daos

import androidx.room.Dao
import androidx.room.Query
import com.wealthpark.exam.core.room.base.BaseRoomDao
import com.wealthpark.exam.core.room.entities.City

@Dao
interface CityDao : BaseRoomDao<City> {

    @Query("SELECT * FROM city")
    suspend fun findAll(): List<City>

    @Query("SELECT * FROM city WHERE id = :id")
    suspend fun find(id: Int): City?

    @Query("SELECT * FROM city WHERE name = :name")
    suspend fun getCityByName(name: String): City?
}