package com.darshan.personalitytest.category.repository

import com.darshan.personalitytest.core.database.room.PersonalityDatabase
import com.darshan.personalitytest.core.database.room.entity.CategoryEntity
import io.reactivex.Single
import javax.inject.Inject

class LoadCategoryRepositoryImpl @Inject constructor(
    private val personalityDatabase: PersonalityDatabase
) : LoadCategoryRepository {

    override fun getCategories(): Single<List<CategoryEntity>> {
        return personalityDatabase.categoryDao().getCategories()
    }

}