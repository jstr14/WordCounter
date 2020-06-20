package com.hector.wordcounter.presentation.documentList.adapter

import android.net.Uri

interface OnDocumentsAdapterListener {

    fun onClickDocument(documentUri: Uri)
}