package com.hector.wordcounter.di.module


import com.hector.wordcounter.di.provider.DocumentListFragmentProvider
import com.hector.wordcounter.presentation.documentDetail.DocumentDetailActivity
import com.hector.wordcounter.presentation.mainActivity.MainActivity
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

    @ContributesAndroidInjector
    fun documentDetailActivityInjector(): DocumentDetailActivity


}