package com.darshan.personalitytest.core.injection

import android.app.Application
import com.darshan.personalitytest.core.PersonalityTestApplication
import com.darshan.personalitytest.core.injection.scopes.PerApplication
import com.darshan.personalitytest.injection.MainActivityBuilderModule
import dagger.BindsInstance
import dagger.Component

@PerApplication
@Component(
    modules = [
        BaseModule::class,
        MainActivityBuilderModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: PersonalityTestApplication)

}