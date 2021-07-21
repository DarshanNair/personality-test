package com.darshan.personalitytest.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.darshan.personalitytest.category.model.Category

abstract class CategoryListViewModel : ViewModel() {

    sealed class State {
        object Loading : State()
        data class Success(val categories: List<Category>) : State()
        object Empty : State()
        object Error : State()
    }

    abstract val state: LiveData<State>

    abstract fun loadCategories()

}
