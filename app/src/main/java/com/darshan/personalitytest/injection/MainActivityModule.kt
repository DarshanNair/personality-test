package com.darshan.personalitytest.injection

import android.content.Context
import com.darshan.core.injection.scopes.PerActivity
import com.darshan.personalitytest.MainActivity
import com.darshan.personalitytest.category.injection.CategoryListFragmentBuilderModule
import com.darshan.personalitytest.question.injection.QuestionsFragmentBuilderModule
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        CategoryListFragmentBuilderModule::class,
        QuestionsFragmentBuilderModule::class
    ]
)
class MainActivityModule {

    @Provides
    @PerActivity
    fun provideContext(activity: MainActivity): Context = activity

}