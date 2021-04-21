package com.wealthpark.exam.core.room.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.wealthpark.exam.core.room.entities.RoomEntity

interface BaseRoomDao<T : RoomEntity> {
    @Insert suspend fun insert(vararg args: T)
    @Update suspend fun update(vararg args: T)
    @Delete suspend fun delete(vararg args: T)
}