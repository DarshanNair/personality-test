package com.darshan.personalitytest.category.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.darshan.personalitytest.category.domain.LoadCategoryUseCase
import javax.inject.Inject

class CategoryListViewModelFactory @Inject constructor(
    private val loadCategoryUseCase: LoadCategoryUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CategoryListViewModelImpl(
            loadCategoryUseCase
        ) as T

}