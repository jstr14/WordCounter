package com.hector.wordcounter.presentation.documentDetail

import com.hector.wordcounter.domain.model.FileInfo

sealed class DocumentDetailState {
    data class Success(val fileInfo: FileInfo, var firstPage: Boolean = true) :
        DocumentDetailState()

    data class Error(
        val isEmptyList: Boolean = false,
        val isErrorAtProcessFile: Boolean = false,
        val queryEmpty: Boolean = false
    ) :
        DocumentDetailState()

    object Loading : DocumentDetailState()
}