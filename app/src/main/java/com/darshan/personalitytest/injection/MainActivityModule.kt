package com.darshan.personalitytest.injection

import android.content.Context
import com.darshan.personalitytest.MainActivity
import com.darshan.personalitytest.category.injection.CategoryListFragmentBuilderModule
import com.darshan.personalitytest.core.injection.scopes.PerActivity
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        CategoryListFragmentBuilderModule::class
    ]
)
class MainActivityModule {

    @Provides
    @PerActivity
    fun provideContext(activity: MainActivity): Context = activity

}