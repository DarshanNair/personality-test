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

    private val questionEntity = QuestionEntity(
        "QUESTION",
        1,
        "CATEGORY",
        "QUESTION_TYPE",
        "[QUESTION_OPTIONS]",
        "QUESTION_OPTION_SELECTED",
        "QUESTION_CONDITION"
    )

    private val questionData = QuestionData(
        1,
        "QUESTION",
        "CATEGORY",
        QuestionType(
            "QUESTION_TYPE",
            "QUESTION_OPTION_SELECTED",
            listOf("QUESTION_OPTIONS")
        )
    )

    @Test
    fun `Get Questions - from database`() {
        //GIVEN
        given(mockPersonalityDatabase.questionDao()).willReturn(mockQuestionDao)
        given(mockQuestionDao.getQuestionsByCategory("CATEGORY"))
            .willReturn(Single.just(listOf(questionEntity)))

        //WHEN
        val singleQuestions = subject.getQuestions("CATEGORY").blockingGet()

        //THEN
        then(mockQuestionDao).should().getQuestionsByCategory("CATEGORY")
        then(mockPersonalityApi).shouldHaveNoInteractions()
        singleQuestions shouldBeEqualTo listOf(questionData)
    }

    @Test
    fun `Get Questions - from network`() {
        //GIVEN
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
                    1,
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

    @Test
    fun `Get Question - from database`() {
        //GIVEN
        given(mockPersonalityDatabase.questionDao()).willReturn(mockQuestionDao)
        given(mockQuestionDao.getQuestion("QUESTION")).willReturn(Single.just(questionEntity))

        //WHEN
        val singleQuestions = subject.getQuestion("QUESTION").blockingGet()

        //THEN
        then(mockQuestionDao).should().getQuestion("QUESTION")
        then(mockPersonalityApi).shouldHaveNoInteractions()
        singleQuestions shouldBeEqualTo questionEntity
    }

    @Test
    fun `Update Question - in database`() {
        //GIVEN
        given(mockPersonalityDatabase.questionDao()).willReturn(mockQuestionDao)

        //WHEN
        val singleQuestions = subject.updateQuestion(questionEntity)

        //THEN
        then(mockQuestionDao).should().updateQuestion(questionEntity)
        then(mockPersonalityApi).shouldHaveNoInteractions()
    }

    @Test
    fun `Get Questions by category - from database`() {
        //GIVEN
        given(mockPersonalityDatabase.questionDao()).willReturn(mockQuestionDao)
        given(mockQuestionDao.getQuestionsByCategory("CATEGORY"))
            .willReturn(Single.just(listOf(questionEntity)))

        //WHEN
        val singleQuestions = subject.getQuestionsByCategory("CATEGORY").blockingGet()

        //THEN
        then(mockQuestionDao).should().getQuestionsByCategory("CATEGORY")
        then(mockPersonalityApi).shouldHaveNoInteractions()
        singleQuestions shouldBeEqualTo listOf(questionEntity)
    }

    @Test
    fun `reset the selected questions`() {
        //GIVEN
        given(mockPersonalityDatabase.questionDao()).willReturn(mockQuestionDao)

        //WHEN
        subject.updateQuestions(listOf(questionEntity))

        //THEN
        then(mockPersonalityDatabase).should().questionDao()
        then(mockQuestionDao).should().updateQuestions(listOf(questionEntity))
        then(mockPersonalityApi).shouldHaveNoMoreInteractions()
        then(mockQuestionDao).shouldHaveNoMoreInteractions()
    }
}