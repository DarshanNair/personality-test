package com.darshan.personalitytest.app.injection

import android.app.Application
import android.content.Context
import com.darshan.core.DeviceManager
import com.darshan.core.injection.qualifiers.ForApplication
import com.darshan.core.injection.qualifiers.ForIoThread
import com.darshan.core.injection.qualifiers.ForMainThread
import com.darshan.core.injection.scopes.PerApplication
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
class BaseModule {

    @Provides
    @ForApplication
    fun provideApplication(application: Application): Application = application

    @Provides
    @ForApplication
    fun provideContext(application: Application): Context = application

    @Provides
    @PerApplication
    @ForIoThread
    fun provideIoScheduler(): Scheduler = Schedulers.io()

    @Provides
    @PerApplication
    @ForMainThread
    fun provideMainScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @PerApplication
    fun provideGson() = Gson()

    @Provides
    @PerApplication
    fun provideDeviceManager(@ForApplication application: Context): DeviceManager = DeviceManager(application)

}
