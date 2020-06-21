package com.hector.wordcounter.presentation.documentList.adapter


interface OnDocumentsAdapterListener {

    fun onClickTextDocument(documentUri: String, fileName: String?)

    fun onClickInvalidDocument()
}