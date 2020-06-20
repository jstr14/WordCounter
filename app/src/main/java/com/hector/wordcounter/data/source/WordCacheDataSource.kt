package com.hector.wordcounter.data.source

import android.net.Uri
import com.hector.wordcounter.domain.model.Word
import javax.inject.Inject

class WordCacheDataSource @Inject constructor() {

    private var documentUri: Uri? = null
    private var wordsFromFile: Collection<Word> = emptyList()

    fun thereAreCachedWordsForDocument(documentUri: Uri) = this.documentUri == documentUri

    fun setWordsList(documentUri: Uri, words: Collection<Word>) {
        this.documentUri = documentUri
        this.wordsFromFile = words
    }

    fun getWords(): Collection<Word> {
        return wordsFromFile
    }
}