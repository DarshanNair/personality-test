package com.darshan.personalitytest.category.view

import com.darshan.core.injection.scopes.PerApplication
import dagger.Module
import dagger.Provides
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Named

@Module
class MockUrlModule {

    @Provides
    @PerApplication
    @Named("baseUrl")
    fun provideBaseUrl() = "http://127.0.0.1:8080/"

}