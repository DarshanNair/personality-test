package com.darshan.personalitytest.app

import android.app.Application
import com.darshan.core.injection.scopes.PerApplication
import com.darshan.coretesting.MockUrlModule
import com.darshan.database.room.injection.PersonalityDatabaseModule
import com.darshan.network.api.injection.PersonalityApiModule
import com.darshan.personalitytest.app.injection.AppComponent
import com.darshan.personalitytest.app.injection.BaseModule
import com.darshan.personalitytest.main.injection.MainActivityBuilderModule
import dagger.BindsInstance
import dagger.Component

@PerApplication
@Component(
    modules = [
        BaseModule::class,
        MockUrlModule::class,
        PersonalityApiModule::class,
        PersonalityDatabaseModule::class,
        MainActivityBuilderModule::class
    ]
)
interface TestAppComponent : AppComponent {

    override fun inject(application: PersonalityTestApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestAppComponent
    }

    fun inject(application: MyPersonalityTestApplication)

}