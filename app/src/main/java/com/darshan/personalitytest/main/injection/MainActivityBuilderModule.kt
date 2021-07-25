package com.darshan.personalitytest.main.injection

import com.darshan.core.injection.scopes.PerActivity
import com.darshan.personalitytest.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityBuilderModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivityActivity(): MainActivity

}