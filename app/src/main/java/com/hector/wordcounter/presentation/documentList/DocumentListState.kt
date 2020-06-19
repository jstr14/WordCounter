package com.hector.wordcounter.presentation.documentList

import com.hector.wordcounter.domain.model.Document
import com.hector.wordcounter.models.ListedFile

sealed class DocumentListState {
    data class Success(val documentList: List<Document>) : DocumentListState()
    object Error : DocumentListState()
    object Loading : DocumentListState()
}