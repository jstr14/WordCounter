package com.hector.wordcounter.di.component

import android.app.Application
import com.hector.wordcounter.WordCounterApplication
import com.hector.wordcounter.di.module.ActivityModule
import com.hector.wordcounter.di.module.ApplicationModule
import com.hector.wordcounter.di.module.DataModule
import com.hector.wordcounter.di.module.DataRepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class,
        ActivityModule::class,
        DataModule::class,
        DataRepositoryModule::class
    ]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: WordCounterApplication)
}