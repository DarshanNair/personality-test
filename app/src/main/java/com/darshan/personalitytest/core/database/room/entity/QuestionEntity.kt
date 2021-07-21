package com.darshan.personalitytest.core.database.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class QuestionEntity(
    @PrimaryKey val question: String,
    val category: String,
    val question_type: String,
    val question_options: String,
    val question_option_selected: String,
    val question_condition: String
)