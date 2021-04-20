package com.wealthpark.exam.core.room.base

import com.wealthpark.exam.core.room.entities.RoomEntity

abstract class BaseRoomRepository<T : RoomEntity, D : BaseRoomDao<T>>(
    private val dao: D
) {
    suspend fun findAll(): List<T> = dao.findAll()
    suspend fun find(id: Int): T? = dao.find(id)
}