package com.foundy.data.di

import com.foundy.data.reference.DatabaseReferenceGetter
import com.foundy.data.reference.KeywordsReferenceGetter
import com.foundy.data.reference.UserKeywordsReferenceGetter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseReferenceModule {

    @Singleton
    @Provides
    @Named("keywords")
    fun provideKeywordsReference(): DatabaseReferenceGetter {
        return KeywordsReferenceGetter()
    }

    @Singleton
    @Provides
    @Named("userKeywords")
    fun provideUserKeywordsReference(): DatabaseReferenceGetter {
        return UserKeywordsReferenceGetter()
    }
}