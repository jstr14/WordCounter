package com.hector.wordcounter.presentation.documentList

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hector.wordcounter.domain.usecase.GetFilesFromUriUseCase
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class DocumentListViewModel @Inject constructor(
    private val getFilesFromUriUseCase: GetFilesFromUriUseCase,
    application: Application,
    private val scope: CoroutineScope
) : AndroidViewModel(application) {

    private val _documents = MutableLiveData<DocumentListState>()
    val documents = _documents

    fun onLoadFiles(directoryUri: Uri) {
        _documents.postValue(DocumentListState.Loading)
        getFilesFromUriUseCase.execute(
            scope,
            GetFilesFromUriUseCase.Parameters(directoryUri)
        ) { result ->
            result.success { documents ->
                if (documents.isEmpty()) {
                    _documents.postValue(DocumentListState.Error)
                }
                _documents.postValue(DocumentListState.Success(documents.toList()))
            }
            result.failure {
                _documents.postValue(DocumentListState.Error)
            }
        }

    }
}