package com.darshan.personalitytest.question.domain.submitcategory.injection

import com.darshan.personalitytest.question.domain.submitcategory.SubmitUseCase
import com.darshan.personalitytest.question.domain.submitcategory.SubmitUseCaseImpl
import com.darshan.personalitytest.question.repository.injection.LoadQuestionRepositoryModule
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Named

@Module(includes = [LoadQuestionRepositoryModule::class])
class SubmitUseCaseModule {

    @Provides
    fun provideSubmitUseCase(usecase: SubmitUseCaseImpl): SubmitUseCase =
        usecase

    @Provides
    @Named("SubmitUseCase")
    fun provideCompositeDisposable() = CompositeDisposable()

}