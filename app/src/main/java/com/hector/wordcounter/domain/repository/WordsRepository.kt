package com.hector.wordcounter.domain.repository

import android.net.Uri
import com.hector.wordcounter.domain.Result
import com.hector.wordcounter.domain.model.Word

interface WordsRepository {

    fun getWordsFromFile(documentUri: Uri): Result<Collection<Word>, Exception>
}