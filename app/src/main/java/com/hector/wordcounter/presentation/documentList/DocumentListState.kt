package com.hector.wordcounter.presentation.documentList

import com.hector.wordcounter.domain.model.Document

sealed class DocumentListState {
    data class Success(val documentList: List<Document>) : DocumentListState()
    object Error : DocumentListState()
    object Loading : DocumentListState()
}