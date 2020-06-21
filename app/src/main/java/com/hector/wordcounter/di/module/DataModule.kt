package com.hector.wordcounter.di.module


import android.app.Application
import com.hector.wordcounter.data.repository.FileRepositoryImpl
import com.hector.wordcounter.data.repository.WordRepositoryImpl
import com.hector.wordcounter.data.source.WordCacheDataSource
import com.hector.wordcounter.data.source.WordDataSource
import com.hector.wordcounter.domain.repository.DocumentRepository
import com.hector.wordcounter.domain.repository.WordsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ApplicationModule::class, DataRepositoryModule::class])
class DataModule {

    @Singleton
    @Provides
    fun provideDocumentRepository(application: Application): DocumentRepository {
        return FileRepositoryImpl(application)
    }

    @Singleton
    @Provides
    fun provideWordRepository(
        wordDataSource: WordDataSource,
        wordCacheDataSource: WordCacheDataSource
    ): WordsRepository {
        return WordRepositoryImpl(wordDataSource, wordCacheDataSource)
    }

}