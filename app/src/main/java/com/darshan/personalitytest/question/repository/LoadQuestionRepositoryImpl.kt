package com.darshan.personalitytest.question.repository

import com.darshan.database.room.PersonalityDatabase
import com.darshan.database.room.entity.QuestionEntity
import com.darshan.network.api.PersonalityApi
import com.darshan.network.model.QuestionData
import com.darshan.network.model.QuestionType
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Path
import javax.inject.Inject

class LoadQuestionRepositoryImpl @Inject constructor(
    private val personalityDatabase: PersonalityDatabase,
    private val personalityApi: PersonalityApi,
    private val gson: Gson
) : LoadQuestionRepository {

    override fun getQuestions(category: String): Single<List<QuestionData>> {
        return personalityDatabase.questionDao().getQuestionsByCategory(category)
            .flatMap { questionEntityList ->
                if (questionEntityList.isEmpty()) {
                    personalityApi.getQuestions(category)
                        .map { questionDataList ->
                            insertIntoDB(questionDataList)
                            questionDataList
                        }
                } else {
                    Single.just(transformDBtoNetworkData(questionEntityList))
                }
            }
    }

    private fun transformDBtoNetworkData(questionEntityList: List<QuestionEntity>): List<QuestionData> {
        val questionDataList = mutableListOf<QuestionData>()
        questionEntityList.forEach {
            questionDataList.add(
                QuestionData(
                    it.id,
                    it.question,
                    it.category,
                    QuestionType(
                        it.question_type,
                        it.question_option_selected,
                        gson.fromJson(
                            it.question_options,
                            List::class.java
                        ) as? List<String> ?: emptyList()
                    )
                )
            )
        }
        return questionDataList
    }

    private fun insertIntoDB(questionDataList: List<QuestionData>) {
        val questionEntityList = mutableListOf<QuestionEntity>()
        questionDataList.forEach {
            questionEntityList.add(
                QuestionEntity(
                    it.question,
                    it.id,
                    it.category,
                    it.questionType.type,
                    gson.toJson(it.questionType.options),
                    it.questionType.selectedOption,
                    ""
                )
            )
        }
        personalityDatabase.questionDao().insertQuestions(questionEntityList)
    }


    override fun getQuestion(question: String): Single<QuestionEntity> {
        return personalityDatabase.questionDao().getQuestion(question)
    }

    override fun updateQuestion(questionEntity: QuestionEntity) {
        personalityDatabase.questionDao().updateQuestion(questionEntity)
    }

    override fun getQuestionsByCategory(category: String): Single<List<QuestionEntity>> {
        return personalityDatabase.questionDao().getQuestionsByCategory(category)
    }

    override fun updateQuestion(id: Int, questionData: QuestionData): Observable<QuestionData> {
        return personalityApi.updateQuestion(id, questionData)
    }
}