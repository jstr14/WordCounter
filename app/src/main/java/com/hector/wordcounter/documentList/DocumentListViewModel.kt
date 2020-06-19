package com.hector.wordcounter.documentList

import android.app.Application
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hector.wordcounter.extensions.toListedFileList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DocumentListViewModel(application: Application) : AndroidViewModel(application) {

    private val _documents = MutableLiveData<DocumentListState>()
    val documents = _documents

    fun onLoadFiles(directoryUri: Uri) {
        _documents.postValue(DocumentListState.Loading)
        val documentsTree = DocumentFile.fromTreeUri(getApplication(), directoryUri) ?: return
        var childDocuments = documentsTree.listFiles().toListedFileList()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                childDocuments = childDocuments.filter { it.mimeType == "text/plain" }
                childDocuments = childDocuments.toMutableList().apply {
                    sortBy { it.name }
                    this
                }
                _documents.postValue(DocumentListState.Success(childDocuments))
            }
        }

        if(childDocuments.isEmpty()) {
            _documents.postValue(DocumentListState.Error)
        } else {
            _documents.postValue(DocumentListState.Success(childDocuments))
        }

    }
}