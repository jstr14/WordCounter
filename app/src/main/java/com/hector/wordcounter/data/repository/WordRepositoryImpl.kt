package com.hector.wordcounter.data.repository

import android.net.Uri
import com.hector.wordcounter.data.source.WordCacheDataSource
import com.hector.wordcounter.data.source.WordDataSource
import com.hector.wordcounter.domain.Result
import com.hector.wordcounter.domain.model.Word
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
}