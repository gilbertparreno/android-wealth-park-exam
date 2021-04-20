package com.wealthpark.exam.core.room.daos

import androidx.room.*
import com.wealthpark.exam.core.room.base.BaseRoomDao
import com.wealthpark.exam.core.room.entities.City

@Dao
interface CityDao : BaseRoomDao<City> {

    @Query("SELECT * FROM city")
    override suspend fun findAll(): List<City>

    @Query("SELECT * FROM city WHERE id = :id")
    override suspend fun find(id: Int): City?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCities(vararg cities: City)

    @Query("SELECT * FROM city WHERE name = :name")
    suspend fun getCityByName(name: String): City?

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateCities(vararg cities: City)
}