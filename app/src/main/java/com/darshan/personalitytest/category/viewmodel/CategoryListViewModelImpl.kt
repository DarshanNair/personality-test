package com.darshan.personalitytest.category.viewmodel

import androidx.lifecycle.MutableLiveData
import com.darshan.personalitytest.category.domain.LoadCategoryUseCase
import com.darshan.personalitytest.category.model.Category
import javax.inject.Inject

class CategoryListViewModelImpl @Inject internal constructor(
    private val loadCategoryUseCase: LoadCategoryUseCase
) : CategoryListViewModel(), LoadCategoryUseCase.Callback {

    init {
        loadCategoryUseCase.setCallback(this)
    }

    override val state = MutableLiveData<State>()

    override fun loadCategories() {
        state.value = State.Loading
        loadCategoryUseCase.execute()
    }

    override fun onCategoryFetchSuccess(categories: List<Category>) {
        if (categories.isEmpty()) {
            state.value = State.Empty
        } else {
            state.value = State.Success(categories)
        }
    }

    override fun onCategoryFetchError(throwable: Throwable) {
        state.value = State.Error
    }

    public override fun onCleared() {
        super.onCleared()
        loadCategoryUseCase.cleanup()
    }

}