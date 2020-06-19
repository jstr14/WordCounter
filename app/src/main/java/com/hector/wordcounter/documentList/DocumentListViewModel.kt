package com.hector.wordcounter.documentList

import android.app.Application
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.AndroidViewModel
import com.hector.wordcounter.extensions.toListedFileList

class DocumentListViewModel(application: Application) : AndroidViewModel(application) {

    fun onLoadFiles(directoryUri: Uri) {
        val documentsTree = DocumentFile.fromTreeUri(getApplication(), directoryUri) ?: return
        var childDocuments = documentsTree.listFiles().toListedFileList()

        childDocuments.size

    }
}