package com.darshan.personalitytest.question.injection

import com.darshan.personalitytest.core.injection.scopes.PerFragment
import com.darshan.personalitytest.question.view.QuestionsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class QuestionsFragmentBuilderModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [QuestionsFragmentModule::class])
    abstract fun bindQuestionsFragment(): QuestionsFragment

}