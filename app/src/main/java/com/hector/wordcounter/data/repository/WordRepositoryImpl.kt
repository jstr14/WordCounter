package com.hector.wordcounter.data.repository

import android.net.Uri
import com.hector.wordcounter.data.source.WordCacheDataSource
import com.hector.wordcounter.data.source.WordDataSource
import com.hector.wordcounter.domain.Result
import com.hector.wordcounter.domain.model.Word
import com.hector.wordcounter.domain.model.WordSortType
import com.hector.wordcounter.domain.repository.WordsRepository
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val wordDataSource: WordDataSource,
    private val wordCacheDataSource: WordCacheDataSource
) : WordsRepository {

    override fun getWordsFromFile(documentUri: Uri): Result<Collection<Word>, Exception> {


        return if (wordCacheDataSource.thereAreCachedWordsForDocument(documentUri)) {
            Result.of {
                wordCacheDataSource.getWords()
            }
        } else {
            val wordsResult = wordDataSource.getWordsFromFile(documentUri)
            wordsResult.map {
                wordCacheDataSource.setWordsList(documentUri, it)
            }
            wordsResult
        }
    }

    override fun getWordsSortByType(wordSortType: WordSortType): Result<Collection<Word>, Exception> {

        return Result.of {
            val words = wordCacheDataSource.getWords()
            when (wordSortType) {
                WordSortType.ALPHABETICALLY -> {
                    words.sortedBy { it.value }
                }
                WordSortType.NUMBER_OF_OCCURRENCES -> {
                    words.sortedByDescending { it.numberOfOccurrences }
                }
                WordSortType.ORIGINAL_POSITION -> {
                    words
                }
            }
        }
    }
}