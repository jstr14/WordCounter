package com.hector.wordcounter.di.module


import com.hector.wordcounter.di.provider.DocumentListFragmentProvider
import com.hector.wordcounter.presentation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
interface ActivityModule {

    @ContributesAndroidInjector(
        modules = [
            DocumentListFragmentProvider::class]
    )
    fun mainActivityInjector(): MainActivity


}