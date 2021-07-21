package com.darshan.personalitytest.core.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.darshan.personalitytest.core.database.room.entity.CategoryEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCategories(categories: List<CategoryEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCategories(categories: CategoryEntity): Completable

    @Query("SELECT * FROM CategoryEntity")
    abstract fun getCategories(): Single<List<CategoryEntity>>

}