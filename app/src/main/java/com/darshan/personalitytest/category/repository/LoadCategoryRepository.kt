package com.darshan.personalitytest.category.repository

import io.reactivex.Single

interface LoadCategoryRepository {

    fun getCategories(): Single<List<String>>

}