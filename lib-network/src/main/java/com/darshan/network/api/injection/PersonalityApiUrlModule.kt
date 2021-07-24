package com.darshan.network.api.injection

import com.darshan.core.injection.scopes.PerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class PersonalityApiUrlModule {

    @Provides
    @PerApplication
    @Named("baseUrl")
    fun provideBaseUrl() = "http://10.0.2.2:3000/"

}