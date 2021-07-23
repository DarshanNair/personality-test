package com.darshan.personalitytest.core.database.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.darshan.personalitytest.core.database.room.dao.CategoryDao
import com.darshan.personalitytest.core.database.room.dao.QuestionDao
import com.darshan.personalitytest.core.database.room.entity.CategoryEntity
import com.darshan.personalitytest.core.database.room.entity.QuestionEntity
import junit.framework.TestCase
import org.amshove.kluent.shouldBeEqualTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PersonalityDatabaseTest : TestCase() {

    private lateinit var database: PersonalityDatabase
    private lateinit var categoryDao: CategoryDao
    private lateinit var questionDao: QuestionDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, PersonalityDatabase::class.java).build()
        categoryDao = database.categoryDao()
        questionDao = database.questionDao()
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

    @Test
    fun writeAndReadQuestions() {
        //GIVEN
        val writeQuestionEntityList = listOf(
            QuestionEntity(
                "QUESTION1",
                "CATEGORY1",
                "QUESTION_TYPE1",
                "QUESTION_OPTIONS1",
                "QUESTION_OPTION_SELECTED1",
                "QUESTION_CONDITION1"
            ),
            QuestionEntity(
                "QUESTION2",
                "CATEGORY2",
                "QUESTION_TYPE2",
                "QUESTION_OPTIONS2",
                "QUESTION_OPTION_SELECTED2",
                "QUESTION_CONDITION2"
            )
        )

        val writeQuestionEntity = QuestionEntity(
            "QUESTION3",
            "CATEGORY3",
            "QUESTION_TYPE3",
            "QUESTION_OPTIONS3",
            "QUESTION_OPTION_SELECTED3",
            "QUESTION_CONDITION3"
        )

        //WHEN
        questionDao.insertQuestions(writeQuestionEntityList)
        questionDao.insertQuestion(writeQuestionEntity)
        val readQuestionEntityList = questionDao.getQuestions().blockingGet()

        //THEN
        readQuestionEntityList shouldBeEqualTo (writeQuestionEntityList + writeQuestionEntity)
    }

    @Test
    fun readQuestion() {
        //GIVEN
        val writeQuestionEntityList = listOf(
            QuestionEntity(
                "QUESTION1",
                "CATEGORY1",
                "QUESTION_TYPE1",
                "QUESTION_OPTIONS1",
                "QUESTION_OPTION_SELECTED1",
                "QUESTION_CONDITION1"
            ),
            QuestionEntity(
                "QUESTION2",
                "CATEGORY2",
                "QUESTION_TYPE2",
                "QUESTION_OPTIONS2",
                "QUESTION_OPTION_SELECTED2",
                "QUESTION_CONDITION2"
            )
        )

        //WHEN
        questionDao.insertQuestions(writeQuestionEntityList)
        val readQuestionEntity = questionDao.getQuestion("QUESTION1").blockingGet()

        //THEN
        readQuestionEntity shouldBeEqualTo writeQuestionEntityList[0]
    }

    @Test
    fun readQuestionsByCategory() {
        //GIVEN
        val writeQuestionEntityList = listOf(
            QuestionEntity(
                "QUESTION1",
                "CATEGORY1",
                "QUESTION_TYPE1",
                "QUESTION_OPTIONS1",
                "QUESTION_OPTION_SELECTED1",
                "QUESTION_CONDITION1"
            ),
            QuestionEntity(
                "QUESTION2",
                "CATEGORY2",
                "QUESTION_TYPE2",
                "QUESTION_OPTIONS2",
                "QUESTION_OPTION_SELECTED2",
                "QUESTION_CONDITION2"
            )
        )

        //WHEN
        questionDao.insertQuestions(writeQuestionEntityList)
        val readQuestionEntity = questionDao.getQuestionsByCategory("CATEGORY2").blockingGet()

        //THEN
        readQuestionEntity shouldBeEqualTo listOf(writeQuestionEntityList[1])
    }

}