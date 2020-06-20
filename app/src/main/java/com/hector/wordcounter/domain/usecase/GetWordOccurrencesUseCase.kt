package com.hector.wordcounter.domain.usecase

import android.net.Uri
import com.hector.wordcounter.domain.Interactor
import com.hector.wordcounter.domain.Result
import com.hector.wordcounter.domain.model.Word
import com.hector.wordcounter.domain.model.WordSortType
import com.hector.wordcounter.domain.repository.WordsRepository
import javax.inject.Inject

class GetWordOccurrencesUseCase @Inject constructor(private val repository: WordsRepository) :
    Interactor<Collection<Word>, GetWordOccurrencesUseCase.Parameters>() {


    override fun run(params: Parameters): Result<Collection<Word>, *> {
        return repository.getWordsFromFile(params.uri, params.sortType, params.query)
    }

    data class Parameters(
        val uri: Uri,
        val sortType: WordSortType = WordSortType.ORIGINAL_POSITION,
        val query: String? = null
    )

}