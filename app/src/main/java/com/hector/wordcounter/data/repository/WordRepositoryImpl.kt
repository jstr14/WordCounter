package com.hector.wordcounter.data.repository

import android.net.Uri
import com.hector.wordcounter.data.source.WordCacheDataSource
import com.hector.wordcounter.data.source.WordDataSource
import com.hector.wordcounter.domain.Result
import com.hector.wordcounter.domain.model.FileInfo
import com.hector.wordcounter.domain.model.Word
import com.hector.wordcounter.domain.model.WordSortType
import com.hector.wordcounter.domain.repository.WordsRepository
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val wordDataSource: WordDataSource,
    private val wordCacheDataSource: WordCacheDataSource
) : WordsRepository {

    override fun getWordsFromFile(
        documentUri: Uri,
        query: String?
    ): Result<FileInfo, Exception> {

        return if (wordCacheDataSource.thereAreCachedWordsForDocument(documentUri)) {
            Result.of {
                wordCacheDataSource.getFileInfo()
            }
        } else {
            val wordsResult = wordDataSource.getWordsFromFile(documentUri)
            wordsResult.map {
                wordCacheDataSource.setFileInfo(it, documentUri)
            }
            wordsResult
        }
    }

    override fun getWordsSortByType(
        wordSortType: WordSortType,
        query: String?
    ): Result<FileInfo, Exception> {

        return Result.of {
            var fileInfo = wordCacheDataSource.getFileInfo()?.copy()
            fileInfo?.let {
                fileInfo.words = sortListByType(fileInfo.words, wordSortType)
                if (query != null) {
                    fileInfo.words =
                        fileInfo.words.filter { it.value.contains(query.toLowerCase()) }
                    fileInfo
                } else {
                    fileInfo
                }
            }
        }
    }

    private fun sortListByType(
        words: Collection<Word>,
        wordSortType: WordSortType
    ): Collection<Word> {
        return when (wordSortType) {
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