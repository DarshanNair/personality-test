package com.darshan.personalitytest.category.repository.injection

import com.darshan.personalitytest.category.repository.LoadCategoryRepository
import com.darshan.personalitytest.category.repository.LoadCategoryRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class LoadCategoryRepositoryModule {

    @Provides
    fun provideLoadCategoryRepository(repository: LoadCategoryRepositoryImpl): LoadCategoryRepository =
        repository

}