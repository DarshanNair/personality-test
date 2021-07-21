package com.darshan.personalitytest.question.repository

import com.darshan.personalitytest.question.model.Question
import io.reactivex.Single
import javax.inject.Inject

class LoadQuestionRepositoryImpl @Inject constructor() : LoadQuestionRepository {

    override fun getQuestions(category: String): Single<List<Question>> {
        //TODO
        return Single.just(
            listOf(
                Question("What is your gender?", listOf("male", "female", "other")),
                Question(
                    "How important is the gender of your partner?",
                    listOf("not important", "important", "very important")
                ),
                Question(
                    "Do any children under the age of 18 live with you?",
                    listOf("yes", "sometimes", "no")
                )
            )
        )
    }

}