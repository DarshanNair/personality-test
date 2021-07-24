package com.darshan.personalitytest.question.repository

import com.darshan.database.room.PersonalityDatabase
import com.darshan.database.room.dao.QuestionDao
import com.darshan.database.room.entity.QuestionEntity
import com.darshan.network.api.PersonalityApi
import com.darshan.network.model.QuestionData
import com.darshan.network.model.QuestionType
import com.google.gson.Gson
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
class LoadQuestionRepositoryTest {

    private lateinit var subject: LoadQuestionRepositoryImpl

    @Mock
    private lateinit var mockPersonalityDatabase: PersonalityDatabase

    @Mock
    private lateinit var mockPersonalityApi: PersonalityApi

    @Mock
    private lateinit var mockQuestionDao: QuestionDao

    private val gson: Gson = Gson()

    @Before
    fun setUp() {
        subject = LoadQuestionRepositoryImpl(mockPersonalityDatabase, mockPersonalityApi, gson)
    }

    @Test
    fun `Get Questions - from database`() {
        //GIVEN
        val questionEntity = QuestionEntity(
            "QUESTION",
            "CATEGORY",
            "QUESTION_TYPE",
            "[QUESTION_OPTIONS]",
            "QUESTION_OPTION_SELECTED",
            "QUESTION_CONDITION"
        )
        given(mockPersonalityDatabase.questionDao()).willReturn(mockQuestionDao)
        given(mockQuestionDao.getQuestionsByCategory("CATEGORY"))
            .willReturn(Single.just(listOf(questionEntity)))

        //WHEN
        val singleQuestions = subject.getQuestions("CATEGORY").blockingGet()

        //THEN
        then(mockQuestionDao).should().getQuestionsByCategory("CATEGORY")
        then(mockPersonalityApi).shouldHaveNoInteractions()
        singleQuestions shouldBeEqualTo listOf(
            QuestionData(
                "QUESTION",
                "CATEGORY",
                QuestionType(
                    "QUESTION_TYPE",
                    "QUESTION_OPTION_SELECTED",
                    listOf("QUESTION_OPTIONS")
                )
            )
        )
    }

    @Test
    fun `Get Questions - from network`() {
        //GIVEN
        val questionData = QuestionData(
            "QUESTION",
            "CATEGORY",
            QuestionType(
                "QUESTION_TYPE",
                "QUESTION_OPTION_SELECTED",
                listOf("QUESTION_OPTIONS")
            )
        )
        given(mockPersonalityDatabase.questionDao()).willReturn(mockQuestionDao)
        given(mockQuestionDao.getQuestionsByCategory("CATEGORY")).willReturn(Single.just(emptyList()))
        given(mockPersonalityApi.getQuestions("CATEGORY"))
            .willReturn(Single.just(listOf(questionData)))

        //WHEN
        val singleQuestions = subject.getQuestions("CATEGORY").blockingGet()

        //THEN
        then(mockQuestionDao).should().getQuestionsByCategory("CATEGORY")
        then(mockPersonalityApi).should().getQuestions("CATEGORY")
        then(mockQuestionDao).should().insertQuestions(
            listOf(
                QuestionEntity(
                    "QUESTION",
                    "CATEGORY",
                    "QUESTION_TYPE",
                    "[\"QUESTION_OPTIONS\"]",
                    "QUESTION_OPTION_SELECTED",
                    ""
                )
            )
        )
        singleQuestions shouldBeEqualTo listOf(questionData)
    }

}