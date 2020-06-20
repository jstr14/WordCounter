package com.hector.wordcounter.extensions

import androidx.documentfile.provider.DocumentFile
import com.hector.wordcounter.domain.model.Document

fun Array<DocumentFile>.toDocumentList(): List<Document> {
    val list = mutableListOf<Document>()
    for (document in this) {
        list += Document(document.name, document.type, document.uri)
    }
    return list
}