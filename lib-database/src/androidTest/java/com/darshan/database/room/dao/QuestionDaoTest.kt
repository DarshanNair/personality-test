package com.darshan.database.room.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.darshan.database.room.PersonalityDatabase
import com.darshan.database.room.entity.QuestionEntity
import junit.framework.TestCase
import org.amshove.kluent.shouldBeEqualTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuestionDaoTest : TestCase() {

    private lateinit var database: PersonalityDatabase
    private lateinit var questionDao: QuestionDao

    private val questionEntity1 = QuestionEntity(
        "QUESTION1",
        1,
        "CATEGORY1",
        "QUESTION_TYPE1",
        "QUESTION_OPTIONS1",
        "QUESTION_OPTION_SELECTED1",
        "QUESTION_CONDITION1"
    )

    private val questionEntity2 = QuestionEntity(
        "QUESTION2",
        2,
        "CATEGORY2",
        "QUESTION_TYPE2",
        "QUESTION_OPTIONS2",
        "QUESTION_OPTION_SELECTED2",
        "QUESTION_CONDITION2"
    )

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, PersonalityDatabase::class.java).build()
        questionDao = database.questionDao()
    }

    @After
    fun closeDB() {
        database.close()
    }

    @Test
    fun writeAndReadQuestions() {
        //GIVEN
        val writeQuestionEntityList = listOf(questionEntity1, questionEntity2)

        //WHEN
        questionDao.insertQuestions(writeQuestionEntityList)
        val readQuestionEntityList = questionDao.getQuestions().blockingGet()

        //THEN
        readQuestionEntityList shouldBeEqualTo writeQuestionEntityList
    }

    @Test
    fun updateAndReadQuestions() {
        //GIVEN
        val writeQuestionEntityList = listOf(questionEntity1, questionEntity2)

        val writeQuestionEntity = QuestionEntity(
            "QUESTION1",
            1,
            "CATEGORY3",
            "QUESTION_TYPE3",
            "QUESTION_OPTIONS3",
            "QUESTION_OPTION_SELECTED3",
            "QUESTION_CONDITION3"
        )

        //WHEN
        questionDao.insertQuestions(writeQuestionEntityList)
        questionDao.updateQuestion(writeQuestionEntity)
        val readQuestionEntityList = questionDao.getQuestions().blockingGet()

        //THEN
        readQuestionEntityList shouldBeEqualTo (listOf(
            writeQuestionEntity,
            writeQuestionEntityList[1]
        ))
    }

    @Test
    fun readQuestion() {
        //GIVEN
        val writeQuestionEntityList = listOf(questionEntity1, questionEntity2)

        //WHEN
        questionDao.insertQuestions(writeQuestionEntityList)
        val readQuestionEntity = questionDao.getQuestion("QUESTION1").blockingGet()

        //THEN
        readQuestionEntity shouldBeEqualTo writeQuestionEntityList[0]
    }

    @Test
    fun readQuestionsByCategory() {
        //GIVEN
        val writeQuestionEntityList = listOf(questionEntity1, questionEntity2)

        //WHEN
        questionDao.insertQuestions(writeQuestionEntityList)
        val readQuestionEntity = questionDao.getQuestionsByCategory("CATEGORY2").blockingGet()

        //THEN
        readQuestionEntity shouldBeEqualTo listOf(writeQuestionEntityList[1])
    }

}