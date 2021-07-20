package com.darshan.personalitytest.core.injection

import android.app.Application
import android.content.Context
import com.darshan.personalitytest.core.injection.qualifiers.ForApplication
import dagger.Module
import dagger.Provides

@Module
class BaseModule {

    @Provides
    @ForApplication
    fun provideContext(application: Application): Context = application

    @Provides
    @ForApplication
    fun provideApplication(application: Application): Application = application

}
