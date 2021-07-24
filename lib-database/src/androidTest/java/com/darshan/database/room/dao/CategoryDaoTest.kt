package com.darshan.database.room.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.darshan.database.room.PersonalityDatabase
import com.darshan.database.room.entity.CategoryEntity
import junit.framework.TestCase
import org.amshove.kluent.shouldBeEqualTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CategoryDaoTest : TestCase() {

    private lateinit var database: PersonalityDatabase
    private lateinit var categoryDao: CategoryDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, PersonalityDatabase::class.java).build()
        categoryDao = database.categoryDao()
    }

    @After
    fun closeDB() {
        database.close()
    }

    @Test
    fun writeAndReadCategories() {
        //GIVEN
        val writeCategoryEntityList = listOf(
            CategoryEntity("CATEGORY1"),
            CategoryEntity("CATEGORY2")
        )

        //WHEN
        categoryDao.insertCategories(writeCategoryEntityList)
        val readCategoryEntityList = categoryDao.getCategories().blockingGet()

        //THEN
        readCategoryEntityList shouldBeEqualTo writeCategoryEntityList
    }

}