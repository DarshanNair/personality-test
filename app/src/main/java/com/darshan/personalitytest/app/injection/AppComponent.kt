package com.darshan.personalitytest.app.injection

import android.app.Application
import com.darshan.core.injection.scopes.PerApplication
import com.darshan.database.room.injection.PersonalityDatabaseModule
import com.darshan.network.api.injection.PersonalityApiModule
import com.darshan.network.api.injection.PersonalityApiUrlModule
import com.darshan.personalitytest.app.PersonalityTestApplication
import com.darshan.personalitytest.main.injection.MainActivityBuilderModule
import dagger.BindsInstance
import dagger.Component

@PerApplication
@Component(
    modules = [
        BaseModule::class,
        PersonalityApiUrlModule::class,
        PersonalityApiModule::class,
        PersonalityDatabaseModule::class,
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