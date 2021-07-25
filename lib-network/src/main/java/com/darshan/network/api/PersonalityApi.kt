package com.darshan.network.api

import com.darshan.network.model.QuestionData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface PersonalityApi {

    @GET("categories")
    fun getCategories(): Single<List<String>>

    @GET("questions")
    fun getQuestions(@Query("category") category: String): Single<List<QuestionData>>

    @PATCH("questions/{id}")
    fun updateQuestion(
        @Path("id") id: Int,
        @Body questionData: QuestionData
    ): Observable<QuestionData>

}