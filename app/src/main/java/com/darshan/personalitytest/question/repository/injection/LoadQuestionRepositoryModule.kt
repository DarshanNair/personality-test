package com.darshan.personalitytest.question.repository.injection

import com.darshan.personalitytest.question.repository.LoadQuestionRepository
import com.darshan.personalitytest.question.repository.LoadQuestionRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class LoadQuestionRepositoryModule {

    @Provides
    fun provideLoadQuestionRepository(repository: LoadQuestionRepositoryImpl): LoadQuestionRepository =
        repository

}