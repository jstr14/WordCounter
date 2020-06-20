package com.hector.wordcounter.di.builder

import androidx.lifecycle.ViewModel
import com.hector.wordcounter.di.ViewModelKey
import com.hector.wordcounter.presentation.documentDetail.DocumentDetailViewModel
import com.hector.wordcounter.presentation.documentList.DocumentListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelsBuilder {

    @Binds
    @IntoMap
    @ViewModelKey(DocumentListViewModel::class)
    abstract fun bindDocumentListViewModel(documentListViewModel: DocumentListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DocumentDetailViewModel::class)
    abstract fun bindDocumentDetailViewModel(documentDetailViewModel: DocumentDetailViewModel): ViewModel

}