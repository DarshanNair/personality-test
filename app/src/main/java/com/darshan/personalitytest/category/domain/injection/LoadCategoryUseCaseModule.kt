package com.darshan.personalitytest.category.domain.injection

import com.darshan.personalitytest.category.domain.LoadCategoryUseCase
import com.darshan.personalitytest.category.domain.LoadCategoryUseCaseImpl
import com.darshan.personalitytest.category.repository.injection.LoadCategoryRepositoryModule
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module(includes = [LoadCategoryRepositoryModule::class])
class LoadCategoryUseCaseModule {

    @Provides
    fun provideLoadCategoryUseCase(usecase: LoadCategoryUseCaseImpl): LoadCategoryUseCase =
        usecase

    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()

}