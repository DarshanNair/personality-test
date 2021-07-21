package com.darshan.personalitytest.category.repository

import com.darshan.personalitytest.core.database.room.entity.CategoryEntity
import io.reactivex.Single

interface LoadCategoryRepository {

    fun getCategories(): Single<List<CategoryEntity>>

}