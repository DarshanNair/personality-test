package com.darshan.personalitytest.question.domain.loadquestion.injection

import com.darshan.personalitytest.question.domain.loadquestion.LoadQuestionUseCase
import com.darshan.personalitytest.question.domain.loadquestion.LoadQuestionUseCaseImpl
import com.darshan.personalitytest.question.repository.injection.LoadQuestionRepositoryModule
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Named

@Module(includes = [LoadQuestionRepositoryModule::class])
class LoadQuestionUseCaseModule {

    @Provides
    fun provideLoadQuestionUseCase(usecase: LoadQuestionUseCaseImpl): LoadQuestionUseCase =
        usecase

    @Provides
    @Named("LoadQuestionUseCase")
    fun provideCompositeDisposable() = CompositeDisposable()

}