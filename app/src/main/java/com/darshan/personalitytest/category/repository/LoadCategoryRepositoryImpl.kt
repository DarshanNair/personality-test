package com.darshan.personalitytest.category.repository

import com.darshan.database.room.PersonalityDatabase
import com.darshan.database.room.entity.CategoryEntity
import com.darshan.network.api.PersonalityApi
import io.reactivex.Single
import javax.inject.Inject

class LoadCategoryRepositoryImpl @Inject constructor(
    private val personalityDatabase: PersonalityDatabase,
    private val personalityApi: PersonalityApi
) : LoadCategoryRepository {

    override fun getCategories(): Single<List<String>> {
        return personalityDatabase.categoryDao().getCategories()
            .flatMap { categoryEntity ->
                if (categoryEntity.isEmpty()) {
                    personalityApi.getCategories()
                        .map { categories ->
                            insertIntoDB(categories)
                            categories
                        }
                } else {
                    Single.just(transformDBtoNetworkData(categoryEntity))
                }
            }
    }

    private fun transformDBtoNetworkData(categoryEntityList: List<CategoryEntity>): List<String> {
        val categories = mutableListOf<String>()
        categoryEntityList.forEach {
            categories.add(
                it.category
            )
        }
        return categories
    }

    private fun insertIntoDB(categories: List<String>) {
        val categoryEntityList = mutableListOf<CategoryEntity>()
        categories.forEach {
            categoryEntityList.add(
                CategoryEntity(
                    it
                )
            )
        }
        personalityDatabase.categoryDao().insertCategories(categoryEntityList)
    }

}