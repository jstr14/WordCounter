package com.hector.wordcounter.domain.usecase

import android.net.Uri
import com.hector.wordcounter.domain.Interactor
import com.hector.wordcounter.domain.Result
import com.hector.wordcounter.domain.model.FileInfo
import com.hector.wordcounter.domain.repository.WordsRepository
import javax.inject.Inject

class GetWordOccurrencesUseCase @Inject constructor(private val repository: WordsRepository) :
    Interactor<FileInfo, GetWordOccurrencesUseCase.Parameters>() {


    override fun run(params: Parameters): Result<FileInfo, *> {
        return repository.getWordsFromFile(params.uri, params.query)
    }

    data class Parameters(
        val uri: Uri,
        val query: String? = null
    )

}