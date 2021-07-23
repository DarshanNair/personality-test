package com.darshan.personalitytest.core.network.api

import com.darshan.personalitytest.core.network.model.QuestionData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PersonalityApi {

    @GET("categories")
    fun getCategories(): Single<List<String>>

    @GET("questions")
    fun getQuestions(@Query("category") category: String): Single<List<QuestionData>>

}