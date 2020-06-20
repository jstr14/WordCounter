package com.hector.wordcounter.di.module

import android.app.Application
import android.content.Context
import com.hector.wordcounter.di.builder.ViewModelFactoryBuilder
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton


@Module(includes = [ViewModelFactoryBuilder::class])
class ApplicationModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    internal fun providesContinuation(): CoroutineScope = CoroutineScope(
        Dispatchers.Main + SupervisorJob()
    )
}