package com.darshan.personalitytest.category.domain

import com.darshan.personalitytest.category.model.Category
import com.darshan.personalitytest.category.repository.LoadCategoryRepository
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoadCategoryUseCaseTest {

    private lateinit var subject: LoadCategoryUseCase

    @Mock
    private lateinit var mockCallback: LoadCategoryUseCase.Callback

    @Mock
    private lateinit var mockThrowable: Throwable

    @Mock
    private lateinit var mockCompositeDisposable: CompositeDisposable

    @Mock
    private lateinit var mockLoadCategoryRepository: LoadCategoryRepository

    @Before
    fun setUp() {
        subject = LoadCategoryUseCaseImpl(
            mockCompositeDisposable,
            mockLoadCategoryRepository,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
        subject.setCallback(mockCallback)
    }

    @Test
    fun `Load Category - Success`() {
        //GIVEN
        val categories = listOf("STRING1", "STRING2")
        given(mockLoadCategoryRepository.getCategories()).willReturn(Single.just(categories))

        // WHEN
        subject.execute()

        // THEN
        then(mockCallback).should()
            .onCategoryFetchSuccess(listOf(Category("STRING1"), Category("STRING2")))
        then(mockCallback).shouldHaveNoMoreInteractions()
    }

    @Test
    fun `Load Category - Error`() {
        // GIVEN
        given(mockLoadCategoryRepository.getCategories()).willReturn(Single.error(mockThrowable))

        // WHEN
        subject.execute()

        // THEN
        then(mockCallback).should().onCategoryFetchError(mockThrowable)
        then(mockCallback).shouldHaveNoMoreInteractions()
    }

    @Test
    fun `cleanup()`() {
        // WHEN
        subject.cleanup()

        // THEN
        then(mockCompositeDisposable).should().clear()
    }

}