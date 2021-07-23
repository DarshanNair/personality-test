package com.darshan.personalitytest.core.network.api.injection

import android.content.Context
import com.darshan.personalitytest.core.injection.qualifiers.ForApplication
import com.darshan.personalitytest.core.injection.scopes.PerApplication
import com.darshan.personalitytest.core.network.api.PersonalityApi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class PersonalityApiModule {

    companion object {
        private const val CACHE_SIZE = 50 * 1024 * 1024
    }

    @Provides
    @PerApplication
    fun provideOkHttpClient(okHttpBuilder: OkHttpClient.Builder): OkHttpClient {
        return okHttpBuilder.build()
    }

    @Provides
    @PerApplication
    fun providePersonalityApi(
        retrofitBuilder: Retrofit.Builder,
        client: OkHttpClient
    ): PersonalityApi {
        return retrofitBuilder.client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://10.0.2.2:3000/")
            .build()
            .create(PersonalityApi::class.java)
    }

    @Provides
    @PerApplication
    fun provideOkHttpClientBuilder(cache: Cache): OkHttpClient.Builder {
        return OkHttpClient().newBuilder()
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .cache(cache)
            .connectTimeout(10L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
    }

    @Provides
    @PerApplication
    fun provideRetrofitBuilder(): Retrofit.Builder = Retrofit.Builder()

    @Provides
    @PerApplication
    fun provideCache(@ForApplication context: Context): Cache =
        Cache(context.cacheDir, CACHE_SIZE.toLong())

}