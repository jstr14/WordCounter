package com.hector.wordcounter.domain.repository

import android.net.Uri
import com.hector.wordcounter.domain.Result
import com.hector.wordcounter.domain.model.Document

interface DocumentRepository {

    fun getFilesFromUri(uri: Uri): Result<Collection<Document>, Exception>
}