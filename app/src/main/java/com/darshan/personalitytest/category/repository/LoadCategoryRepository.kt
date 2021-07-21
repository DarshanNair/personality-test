package com.darshan.personalitytest.category.repository

import com.darshan.personalitytest.category.model.Category
import io.reactivex.Single

interface LoadCategoryRepository {

    fun getCategories(): Single<List<Category>>

}