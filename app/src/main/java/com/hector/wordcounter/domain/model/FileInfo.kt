package com.hector.wordcounter.domain.model

data class FileInfo(val totalNumberOfWords: Int, var words: Collection<Word>, val totalItemCount: Int)