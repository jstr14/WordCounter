package com.hector.wordcounter.presentation.documentDetail

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hector.wordcounter.domain.model.WordSortType
import com.hector.wordcounter.domain.usecase.GetWordOccurrencesUseCase
import com.hector.wordcounter.domain.usecase.GetWordsBySortTypeUseCase
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class DocumentDetailViewModel @Inject constructor(
    private val getWordOccurrencesUseCase: GetWordOccurrencesUseCase,
    private val getWordsBySortTypeUseCase: GetWordsBySortTypeUseCase,
    private val scope: CoroutineScope
) : ViewModel() {

    private var sortType: WordSortType = WordSortType.ORIGINAL_POSITION

    private val _documentDetailState = MutableLiveData<DocumentDetailState>()
    val documentDetailState = _documentDetailState

    fun getWordOccurrencesForDocument(documentUri: Uri) {
        _documentDetailState.postValue(DocumentDetailState.Loading)
        getWordOccurrencesUseCase.execute(
            scope,
            GetWordOccurrencesUseCase.Parameters(documentUri)
        ) { result ->
            result.success {
                if (it.isNotEmpty()) {
                    _documentDetailState.postValue(DocumentDetailState.Success(it.toList()))
                } else {
                    _documentDetailState.postValue(DocumentDetailState.Error(isEmptyList = true))
                }
            }
            result.failure {
                _documentDetailState.postValue(DocumentDetailState.Error(isErrorAtProcessFile = true))
            }
        }
    }

    fun sortByType(sortTypeValue: String) {

        _documentDetailState.postValue(DocumentDetailState.Loading)

        this.sortType = WordSortType.from(sortTypeValue.toLowerCase())

        getWordsBySortTypeUseCase.execute(
            scope,
            GetWordsBySortTypeUseCase.Parameters(sortType)
        ) { result ->
            result.success {
                if (it.isNotEmpty()) {
                    _documentDetailState.postValue(DocumentDetailState.Success(it.toList()))
                } else {
                    _documentDetailState.postValue(DocumentDetailState.Error(isEmptyList = true))
                }
            }
            result.failure {
                it.localizedMessage
            }

        }

    }

}