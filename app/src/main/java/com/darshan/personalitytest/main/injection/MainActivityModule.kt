package com.darshan.personalitytest.main.injection

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.darshan.core.injection.scopes.PerActivity
import com.darshan.personalitytest.main.MainActivity
import com.darshan.personalitytest.category.injection.CategoryListFragmentBuilderModule
import com.darshan.personalitytest.main.SubmitProgress
import com.darshan.personalitytest.main.viewmodel.SharedViewModel
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

    @Provides
    @PerActivity
    fun provideSharedViewModel(activity: MainActivity): SharedViewModel =
        ViewModelProvider(activity).get(SharedViewModel::class.java)

    @Provides
    @PerActivity
    fun provideSubmitProgress(context: Context) = SubmitProgress(context)
}