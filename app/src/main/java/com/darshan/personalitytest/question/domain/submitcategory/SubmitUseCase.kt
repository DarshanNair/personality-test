package com.darshan.personalitytest.question.domain.submitcategory

import com.darshan.core.domain.UseCase
import com.darshan.personalitytest.question.model.Question

interface SubmitUseCase : UseCase {

    fun execute(question: Question)

}