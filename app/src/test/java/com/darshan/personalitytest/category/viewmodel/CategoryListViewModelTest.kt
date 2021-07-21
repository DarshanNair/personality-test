package com.darshan.personalitytest.category.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.darshan.personalitytest.category.domain.LoadCategoryUseCase
import com.darshan.personalitytest.category.model.Category
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CategoryListViewModelTest {

    private lateinit var subject: CategoryListViewModelImpl

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockLoadCategoryUseCase: LoadCategoryUseCase

    @Mock
    private lateinit var mockObserver: Observer<CategoryListViewModel.State>

    @Mock
    private lateinit var mockThrowable: Throwable

    @Mock
    private lateinit var mockCategory: Category

    private lateinit var stateLiveData: MutableLiveData<CategoryListViewModel.State>


    @Before
    fun setUp() {
        stateLiveData = MutableLiveData()
        subject = CategoryListViewModelImpl(mockLoadCategoryUseCase)
        subject.state.observeForever(mockObserver)
    }

    @Test
    fun `Load categories`() {
        // GIVEN
        Mockito.reset(mockLoadCategoryUseCase)

        //WHEN
        subject.loadCategories()

        //THEN
        thenObserverShouldReceiveCorrectStates(CategoryListViewModel.State.Loading)
        then(mockLoadCategoryUseCase).should().execute()
        then(mockLoadCategoryUseCase).shouldHaveNoMoreInteractions()
    }

    @Test
    fun `Category Loaded - Success`() {
        // GIVEN
        val categories = listOf(mockCategory)

        // WHEN
        subject.onCategoryFetchSuccess(categories)

        // THEN
        thenObserverShouldReceiveCorrectStates(CategoryListViewModel.State.Success(categories))
    }

    @Test
    fun `Category Loaded - Empty`() {
        // WHEN
        subject.onCategoryFetchSuccess(emptyList())

        // THEN
        thenObserverShouldReceiveCorrectStates(CategoryListViewModel.State.Empty)
    }

    @Test
    fun `Category Loaded - Error`() {
        // WHEN
        subject.onCategoryFetchError(mockThrowable)

        // THEN
        thenObserverShouldReceiveCorrectStates(CategoryListViewModel.State.Error)
    }

    @Test
    fun `On Cleared`() {
        // GIVEN
        Mockito.reset(mockLoadCategoryUseCase)

        // WHEN
        subject.onCleared()

        // THEN
        then(mockLoadCategoryUseCase).should().cleanup()
        then(mockLoadCategoryUseCase).shouldHaveNoMoreInteractions()
    }

    private fun thenObserverShouldReceiveCorrectStates(vararg expected: CategoryListViewModel.State) {
        expected.forEach { then(mockObserver).should().onChanged(it) }
        then(mockObserver).shouldHaveNoMoreInteractions()
    }

}