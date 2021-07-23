package com.darshan.personalitytest.question.domain.updatequestion.injection

import com.darshan.personalitytest.question.domain.updatequestion.UpdateQuestionUseCase
import com.darshan.personalitytest.question.domain.updatequestion.UpdateQuestionUseCaseImpl
import com.darshan.personalitytest.question.repository.injection.LoadQuestionRepositoryModule
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Named

@Module(includes = [LoadQuestionRepositoryModule::class])
class UpdateQuestionUseCaseModule {

    @Provides
    fun provideUpdateQuestionUseCase(usecase: UpdateQuestionUseCaseImpl): UpdateQuestionUseCase =
        usecase

    @Provides
    @Named("UpdateQuestionUseCase")
    fun provideCompositeDisposable() = CompositeDisposable()

}