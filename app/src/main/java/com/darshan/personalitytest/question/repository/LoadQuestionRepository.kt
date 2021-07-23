package com.darshan.personalitytest.question.repository

import com.darshan.personalitytest.core.database.room.entity.QuestionEntity
import com.darshan.personalitytest.core.network.model.QuestionData
import io.reactivex.Single

interface LoadQuestionRepository {

    fun getQuestions(category: String): Single<List<QuestionData>>

    fun getQuestion(question: String): Single<QuestionEntity>

    fun insertQuestions(questionEntity: QuestionEntity)

    fun getQuestionsByCategory(category: String): Single<List<QuestionEntity>>

}