package com.hector.wordcounter.presentation.documentList

import com.hector.wordcounter.domain.model.DocumentsFolder

sealed class DocumentListState {
    data class Success(val documentsFolder: DocumentsFolder) : DocumentListState()
    data class Error(val isEmpty: Boolean = false) : DocumentListState()
    object Loading : DocumentListState()
}