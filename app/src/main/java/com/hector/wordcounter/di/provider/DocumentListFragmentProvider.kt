package com.hector.wordcounter.di.provider

import com.hector.wordcounter.presentation.documentList.DocumentListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DocumentListFragmentProvider {

    @ContributesAndroidInjector
    abstract fun provideDocumentListFragment(): DocumentListFragment

}