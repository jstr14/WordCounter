package com.hector.wordcounter.data.repository

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.hector.wordcounter.domain.Result
import com.hector.wordcounter.domain.model.Document
import com.hector.wordcounter.domain.repository.DocumentRepository
import com.hector.wordcounter.extensions.toDocumentList

class FileRepositoryImpl(private val application: Context) : DocumentRepository {

    override fun getFilesFromUri(uri: Uri): Result<Collection<Document>, Exception> {

        return Result.of {
            val documentsTree =
                DocumentFile.fromTreeUri(application, uri) ?: throw Exception("Invalid Uri")
            var childDocuments = documentsTree.listFiles().toDocumentList()
            childDocuments
        }
    }

}