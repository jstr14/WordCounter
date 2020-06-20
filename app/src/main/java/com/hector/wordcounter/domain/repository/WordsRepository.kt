package com.hector.wordcounter.domain.repository

import android.net.Uri
import com.hector.wordcounter.domain.Result
import com.hector.wordcounter.domain.model.Word
import com.hector.wordcounter.domain.model.WordSortType

interface WordsRepository {

    fun getWordsFromFile(documentUri: Uri): Result<Collection<Word>, Exception>
    fun getWordsSortByType(wordSortType: WordSortType): Result<Collection<Word>, Exception>
}