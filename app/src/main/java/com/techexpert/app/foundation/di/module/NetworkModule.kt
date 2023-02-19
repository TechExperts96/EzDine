package com.techexpert.app.foundation.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.techexpert.app.BuildConfig
import com.techexpert.app.foundation.network.ResultCallAdapterFactory
import com.techexpert.app.foundation.network.interfaces.RBService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesBaseUrl(): String = BuildConfig.HOST_URL

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        } else {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun providesHeaders(): Interceptor {
        return Interceptor.invoke {
            it.run {
                proceed(
                    request().newBuilder()
//                        .addHeader("Authorization", "Bearer ${ForgeRockUtils.getAccessToken()}")
                        .build()
                )
            }
        }
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        headersInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES).callTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(headersInterceptor)
            .addInterceptor(httpLoggingInterceptor) // HttpLogging have to be on the last interceptor
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofitBuilder(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder() // ktlint-disable max-line-length
            .baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory()).client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): RBService = retrofit.create(RBService::class.java)

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return GsonBuilder().create()
    }
}
