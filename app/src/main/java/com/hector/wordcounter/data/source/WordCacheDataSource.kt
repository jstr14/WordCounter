package com.hector.wordcounter.data.source

import android.net.Uri
import com.hector.wordcounter.domain.model.FileInfo
import javax.inject.Inject

class WordCacheDataSource @Inject constructor() {

    private var documentUri: Uri? = null
    private var fileInfo: FileInfo? = null

    fun thereAreCachedWordsForDocument(documentUri: Uri) = this.documentUri == documentUri

    fun setFileInfo(fileInfo: FileInfo, documentUri: Uri){
        this.fileInfo = fileInfo
        this.documentUri = documentUri
    }

    fun getFileInfo(): FileInfo? = fileInfo
}