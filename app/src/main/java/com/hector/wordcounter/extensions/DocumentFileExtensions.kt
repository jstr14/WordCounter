package com.hector.wordcounter.extensions

import androidx.documentfile.provider.DocumentFile
import com.hector.wordcounter.domain.model.Document
import com.hector.wordcounter.models.ListedFile

fun Array<DocumentFile>.toListedFileList(): List<ListedFile> {
    val list = mutableListOf<ListedFile>()
    for (document in this) {
        list += ListedFile(document.name, document.type, document.uri)
    }
    return list
}

fun Array<DocumentFile>.toDocumentList(): List<Document> {
    val list = mutableListOf<Document>()
    for (document in this) {
        list += Document(document.name, document.type, document.uri)
    }
    return list
}