package com.darshan.personalitytest.category.repository

import com.darshan.personalitytest.category.model.Category
import io.reactivex.Single
import javax.inject.Inject

class LoadCategoryRepositoryImpl @Inject constructor() : LoadCategoryRepository {

    override fun getCategories(): Single<List<Category>> {
        //TODO
        return Single.just(
            listOf(
                Category("hard_fact"),
                Category("lifestyle"),
                Category("introversion"),
                Category("passion")
            )
        )
    }

}