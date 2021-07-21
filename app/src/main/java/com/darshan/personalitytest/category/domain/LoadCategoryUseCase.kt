package com.darshan.personalitytest.category.domain

import com.darshan.personalitytest.category.model.Category
import com.darshan.personalitytest.core.domain.UseCase

interface LoadCategoryUseCase : UseCase {

    fun execute()

    fun setCallback(callback: Callback)

    interface Callback {
        fun onCategoryFetchSuccess(categories: List<Category>)
        fun onCategoryFetchError(throwable: Throwable)
    }

}