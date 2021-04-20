package com.wealthpark.exam.core.room.repositories

import com.wealthpark.exam.core.room.base.BaseRoomRepository
import com.wealthpark.exam.core.room.daos.CityDao
import com.wealthpark.exam.core.room.entities.City
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityRepository @Inject constructor(
    private val cityDao: CityDao
) : BaseRoomRepository<City, CityDao>(cityDao) {
    suspend fun insertCities(vararg cities: City) = cityDao.insertCities(*cities)
    suspend fun getCityByName(name: String) = cityDao.getCityByName(name)
    suspend fun updateCities(vararg cities: City) = cityDao.updateCities(*cities)
}