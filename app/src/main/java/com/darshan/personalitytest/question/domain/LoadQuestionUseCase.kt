package com.darshan.personalitytest.question.domain

import com.darshan.personalitytest.core.domain.UseCase
import com.darshan.personalitytest.question.model.Question

interface LoadQuestionUseCase : UseCase {

    fun execute(category: String)

    fun setCallback(callback: Callback)

    interface Callback {
        fun onQuestionFetchSuccess(questions: List<Question>)
        fun onQuestionFetchError(throwable: Throwable)
    }

}