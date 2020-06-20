package com.hector.wordcounter.domain.usecase

import com.hector.wordcounter.domain.Interactor
import com.hector.wordcounter.domain.Result
import com.hector.wordcounter.domain.model.Word
import com.hector.wordcounter.domain.model.WordSortType
import com.hector.wordcounter.domain.repository.WordsRepository
import javax.inject.Inject

class GetWordsBySortTypeUseCase @Inject constructor(private val repository: WordsRepository) :
    Interactor<Collection<Word>, GetWordsBySortTypeUseCase.Parameters>() {


    override fun run(params: Parameters): Result<Collection<Word>, *> {
        return repository.getWordsSortByType(params.sortType)
    }

    data class Parameters(val sortType: WordSortType)

}