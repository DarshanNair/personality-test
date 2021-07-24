package com.darshan.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.darshan.database.room.dao.CategoryDao
import com.darshan.database.room.dao.QuestionDao
import com.darshan.database.room.entity.CategoryEntity
import com.darshan.database.room.entity.QuestionEntity

@Database(
    entities = [
        CategoryEntity::class,
        QuestionEntity::class
    ],
    version = 1
)
abstract class PersonalityDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    abstract fun questionDao(): QuestionDao

}