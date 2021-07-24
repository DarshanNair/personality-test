package com.darshan.personalitytest.category.repository

import com.darshan.database.room.PersonalityDatabase
import com.darshan.database.room.dao.CategoryDao
import com.darshan.database.room.entity.CategoryEntity
import com.darshan.network.api.PersonalityApi
import io.reactivex.Single
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoadCategoryRepositoryTest {

    private lateinit var subject: LoadCategoryRepositoryImpl

    @Mock
    private lateinit var mockPersonalityDatabase: PersonalityDatabase

    @Mock
    private lateinit var mockPersonalityApi: PersonalityApi

    @Mock
    private lateinit var mockCategoryDao: CategoryDao

    @Before
    fun setUp() {
        subject = LoadCategoryRepositoryImpl(mockPersonalityDatabase, mockPersonalityApi)
    }

    @Test
    fun `Get Categories - from database`() {
        //GIVEN
        val categoryEntity = CategoryEntity("CATEGORY")
        given(mockPersonalityDatabase.categoryDao()).willReturn(mockCategoryDao)
        given(mockCategoryDao.getCategories()).willReturn(Single.just(listOf(categoryEntity)))

        //WHEN
        val singleCategories = subject.getCategories().blockingGet()

        //THEN
        then(mockCategoryDao).should().getCategories()
        singleCategories shouldBeEqualTo listOf("CATEGORY")
        then(mockPersonalityApi).shouldHaveNoInteractions()
    }

    @Test
    fun `Get Categories - from network`() {
        //GIVEN
        given(mockPersonalityDatabase.categoryDao()).willReturn(mockCategoryDao)
        given(mockCategoryDao.getCategories()).willReturn(Single.just(emptyList()))
        given(mockPersonalityApi.getCategories()).willReturn(Single.just(listOf("CATEGORY")))

        //WHEN
        val singleCategories = subject.getCategories().blockingGet()

        //THEN
        then(mockCategoryDao).should().getCategories()
        then(mockPersonalityApi).should().getCategories()
        then(mockCategoryDao).should().insertCategories(listOf(CategoryEntity("CATEGORY")))
        singleCategories shouldBeEqualTo listOf("CATEGORY")
    }

}