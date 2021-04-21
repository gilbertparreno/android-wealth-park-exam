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

    override suspend fun findAll(): List<City> = cityDao.findAll()

    override suspend fun find(id: Int) = cityDao.find(id)

    suspend fun getCityByName(name: String) = cityDao.getCityByName(name)
}