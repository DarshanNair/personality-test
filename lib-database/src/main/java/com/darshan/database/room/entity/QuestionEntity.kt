package com.darshan.database.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class QuestionEntity(
    @PrimaryKey val question: String,
    val id: Int,    //"json-server" Hack -> To make PATCH call work
    val category: String,
    val question_type: String,
    val question_options: String,
    var question_option_selected: String = "",
    val question_condition: String = ""
)