package com.wealthpark.exam.core.room.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["name", "imageUrl", "description"], unique = true)])
data class City(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val imageUrl: String,
    val description: String
) : RoomEntity