package com.hector.wordcounter.di.module

import android.app.Application
import com.hector.wordcounter.data.source.WordCacheDataSource
import com.hector.wordcounter.data.source.WordDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataRepositoryModule {

    @Provides
    internal fun provideWordDataSource(application: Application): WordDataSource {
        return WordDataSource(application)
    }

    @Singleton
    @Provides
    internal fun provideWordCacheDataSource(): WordCacheDataSource {
        return WordCacheDataSource()
    }
}