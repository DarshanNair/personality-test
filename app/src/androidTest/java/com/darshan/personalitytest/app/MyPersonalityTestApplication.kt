package com.darshan.personalitytest.app

import android.app.Activity
import dagger.android.AndroidInjector

open class MyPersonalityTestApplication : PersonalityTestApplication() {

    override fun onCreate() {
        DaggerTestAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector

}