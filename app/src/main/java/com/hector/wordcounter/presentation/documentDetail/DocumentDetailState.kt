package com.hector.wordcounter.presentation.documentDetail

import com.hector.wordcounter.domain.model.Word

sealed class DocumentDetailState {
    data class Success(val wordList: List<Word>) : DocumentDetailState()
    data class Error(val isEmptyList: Boolean = false, val isErrorAtProcessFile: Boolean = false) :
        DocumentDetailState()

    object Loading : DocumentDetailState()
}