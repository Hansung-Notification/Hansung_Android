package com.foundy.data.di

import com.foundy.data.api.NoticeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        const val NOTICE_BASE_URL = "https://www.hansung.ac.kr/hansung/8385/"
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)

        return client.build()
    }

    @Provides
    @Singleton
    @Named("Notice")
    fun provideNoticeRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NOTICE_BASE_URL)
            .client(okHttpClient)
            .client(provideHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNoticeApiService(@Named("Notice") retrofit: Retrofit): NoticeApi {
        return retrofit.create(NoticeApi::class.java)
    }
}