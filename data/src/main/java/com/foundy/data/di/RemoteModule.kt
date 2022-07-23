package com.foundy.data.di

import com.foundy.data.api.CafeteriaApi
import com.foundy.data.source.cafeteria.CafeteriaRemoteDataSource
import com.foundy.data.source.cafeteria.CafeteriaRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Provides
    @Singleton
    fun provideCafeteriaRemoteDataSource(api: CafeteriaApi): CafeteriaRemoteDataSource {
        return CafeteriaRemoteDataSourceImpl(api)
    }
}