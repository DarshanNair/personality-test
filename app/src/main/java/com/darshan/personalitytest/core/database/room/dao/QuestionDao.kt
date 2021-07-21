package com.darshan.personalitytest.core.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.darshan.personalitytest.core.database.room.entity.QuestionEntity
import io.reactivex.Single

@Dao
abstract class QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertQuestions(questions: List<QuestionEntity>)

    @Query("SELECT * FROM QuestionEntity")
    abstract fun getQuestions(): Single<QuestionEntity>

}