package com.hector.wordcounter.domain.repository

import android.net.Uri
import com.hector.wordcounter.domain.Result
import com.hector.wordcounter.domain.model.FileInfo
import com.hector.wordcounter.domain.model.Word
import com.hector.wordcounter.domain.model.WordSortType

interface WordsRepository {

    fun getWordsFromFile(
        documentUri: Uri,
        query: String?
    ): Result<FileInfo, Exception>

    fun getWordsSortByType(wordSortType: WordSortType, query: String?): Result<FileInfo, Exception>
}