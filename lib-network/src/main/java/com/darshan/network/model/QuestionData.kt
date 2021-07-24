package com.darshan.network.model

import com.google.gson.annotations.SerializedName

data class QuestionData(
    @SerializedName("question") val question: String,
    @SerializedName("category") val category: String,
    @SerializedName("question_type") val questionType: QuestionType
)

data class QuestionType(
    @SerializedName("type") val type: String,
    @SerializedName("selected_option") val selectedOption: String,
    @SerializedName("options") val options: List<String>,
)