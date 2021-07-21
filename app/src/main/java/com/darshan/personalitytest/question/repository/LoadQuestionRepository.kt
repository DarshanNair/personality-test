package com.darshan.personalitytest.question.repository

import com.darshan.personalitytest.question.model.Question
import io.reactivex.Single

interface LoadQuestionRepository {

    fun getQuestions(category: String): Single<List<Question>>

}