package com.darshan.personalitytest.category.view

import android.app.Activity
import com.darshan.personalitytest.app.PersonalityTestApplication
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