package com.hector.wordcounter.presentation.documentList.adapter


interface OnDocumentsAdapterListener {

    fun onClickDocument(documentUri: String, fileName: String?)
}