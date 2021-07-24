package com.darshan.coretesting

import com.darshan.core.injection.scopes.PerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class MockUrlModule {

    @Provides
    @PerApplication
    @Named("baseUrl")
    fun provideBaseUrl() = "http://127.0.0.1:8080/"

}