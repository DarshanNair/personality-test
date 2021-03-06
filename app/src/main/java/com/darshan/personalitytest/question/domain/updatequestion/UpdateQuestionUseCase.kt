package com.darshan.personalitytest.question.domain.updatequestion

import com.darshan.core.domain.UseCase
import com.darshan.personalitytest.question.model.Question

interface UpdateQuestionUseCase : UseCase {

    fun execute(question: Question)

}