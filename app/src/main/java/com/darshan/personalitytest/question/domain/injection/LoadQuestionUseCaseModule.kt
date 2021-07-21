package com.darshan.personalitytest.question.domain.injection

import com.darshan.personalitytest.question.domain.LoadQuestionUseCase
import com.darshan.personalitytest.question.domain.LoadQuestionUseCaseImpl
import com.darshan.personalitytest.question.repository.injection.LoadQuestionRepositoryModule
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module(includes = [LoadQuestionRepositoryModule::class])
class LoadQuestionUseCaseModule {

    @Provides
    fun provideLoadQuestionUseCase(usecase: LoadQuestionUseCaseImpl): LoadQuestionUseCase =
        usecase

    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()

}