package com.hector.wordcounter.domain.repository

import android.net.Uri
import com.hector.wordcounter.domain.Result
import com.hector.wordcounter.domain.model.Word
import com.hector.wordcounter.domain.model.WordSortType

interface WordsRepository {

    fun getWordsFromFile(
        documentUri: Uri,
        sortType: WordSortType,
        query: String?
    ): Result<Collection<Word>, Exception>

    fun getWordsSortByType(wordSortType: WordSortType, query: String?): Result<Collection<Word>, Exception>
}