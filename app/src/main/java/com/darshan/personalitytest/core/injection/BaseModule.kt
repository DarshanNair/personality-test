package com.darshan.personalitytest.core.injection

import android.app.Application
import android.content.Context
import com.darshan.personalitytest.core.injection.qualifiers.ForApplication
import com.darshan.personalitytest.core.injection.qualifiers.ForIoThread
import com.darshan.personalitytest.core.injection.qualifiers.ForMainThread
import com.darshan.personalitytest.core.injection.scopes.PerApplication
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
class BaseModule {

    @Provides
    @ForApplication
    fun provideContext(application: Application): Context = application

    @Provides
    @ForApplication
    fun provideApplication(application: Application): Application = application

    @Provides
    @PerApplication
    @ForIoThread
    fun provideIoScheduler(): Scheduler = Schedulers.io()

    @Provides
    @PerApplication
    @ForMainThread
    fun provideMainScheduler(): Scheduler = AndroidSchedulers.mainThread()

}
