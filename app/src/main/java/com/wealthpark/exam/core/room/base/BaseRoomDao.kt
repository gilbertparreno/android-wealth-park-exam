package com.wealthpark.exam.core.room.base

import com.wealthpark.exam.core.room.entities.RoomEntity

interface BaseRoomDao<T : RoomEntity> {
    suspend fun findAll(): List<@JvmSuppressWildcards T>
    suspend fun find(id: Int): T?
}