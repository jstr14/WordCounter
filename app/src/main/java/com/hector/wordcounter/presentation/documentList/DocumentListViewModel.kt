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

    private val _documentListState = MutableLiveData<DocumentListState>()
    val documentListState = _documentListState

    fun onLoadFiles(directoryUri: Uri) {
        _documentListState.postValue(DocumentListState.Loading)
        getFilesFromUriUseCase.execute(
            scope,
            GetFilesFromUriUseCase.Parameters(directoryUri)
        ) { result ->
            result.success { documents ->
                if (documents.isEmpty()) {
                    _documentListState.postValue(DocumentListState.Error)
                }
                _documentListState.postValue(DocumentListState.Success(documents.toList()))
            }
            result.failure {
                _documentListState.postValue(DocumentListState.Error)
            }
        }

    }
}