package com.hector.wordcounter.documentList

import com.hector.wordcounter.models.ListedFile

sealed class DocumentListState {
    data class Success(val listedFileList: List<ListedFile>) : DocumentListState()
    object Error : DocumentListState()
    object Loading : DocumentListState()
}