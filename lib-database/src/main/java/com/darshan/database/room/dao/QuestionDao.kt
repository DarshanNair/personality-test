package com.darshan.database.room.dao

import androidx.room.*
import com.darshan.database.room.entity.QuestionEntity
import io.reactivex.Single

@Dao
abstract class QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertQuestions(questions: List<QuestionEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateQuestion(question: QuestionEntity)

    @Query("SELECT * FROM QuestionEntity")
    abstract fun getQuestions(): Single<List<QuestionEntity>>

    @Query("SELECT * FROM QuestionEntity WHERE question = :question")
    abstract fun getQuestion(question: String): Single<QuestionEntity>

    @Query("SELECT * FROM QuestionEntity WHERE category = :category")
    abstract fun getQuestionsByCategory(category: String): Single<List<QuestionEntity>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateQuestions(question: List<QuestionEntity>)

}