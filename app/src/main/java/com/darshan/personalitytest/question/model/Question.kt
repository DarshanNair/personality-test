package com.darshan.personalitytest.question.model

data class Question(
    val question: String,
    val category: String,
    val options: List<String>,
    var selectedOption: String? = null
)