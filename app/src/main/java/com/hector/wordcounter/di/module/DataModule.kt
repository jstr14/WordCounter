package com.hector.wordcounter.di.module


import android.app.Application
import com.hector.wordcounter.data.repository.FileRepositoryImpl
import com.hector.wordcounter.domain.repository.DocumentRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ApplicationModule::class])
class DataModule {

    @Singleton
    @Provides
    fun provideAlbumRepository(application: Application): DocumentRepository {
        return FileRepositoryImpl(application)
    }

}