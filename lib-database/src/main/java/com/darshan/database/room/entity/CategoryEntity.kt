package com.darshan.database.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class CategoryEntity(
    @PrimaryKey val category: String,
)