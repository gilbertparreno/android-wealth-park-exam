package com.wealthpark.exam.core.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wealthpark.exam.core.room.daos.CityDao
import com.wealthpark.exam.core.room.daos.FoodDao
import com.wealthpark.exam.core.room.entities.City
import com.wealthpark.exam.core.room.entities.Food

@Database(entities = [Food::class, City::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
    abstract fun cityDao(): CityDao
}