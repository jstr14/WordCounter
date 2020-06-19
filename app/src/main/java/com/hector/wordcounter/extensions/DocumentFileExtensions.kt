package com.hector.wordcounter.extensions

import androidx.documentfile.provider.DocumentFile
import com.hector.wordcounter.models.ListedFile

fun Array<DocumentFile>.toListedFileList(): List<ListedFile> {
    val list = mutableListOf<ListedFile>()
    for (document in this) {
        list += ListedFile(document.name ?: "", document.uri)
    }
    return list
}