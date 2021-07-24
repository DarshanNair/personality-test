package com.darshan.personalitytest.category.domain

import com.darshan.core.domain.UseCase
import com.darshan.personalitytest.category.model.Category

interface LoadCategoryUseCase : UseCase {

    fun execute()

    fun setCallback(callback: Callback)

    interface Callback {
        fun onCategoryFetchSuccess(categories: List<Category>)
        fun onCategoryFetchError(throwable: Throwable)
    }

}