package com.hector.wordcounter.data.source

import android.content.Context
import android.net.Uri
import com.hector.wordcounter.domain.Result
import com.hector.wordcounter.domain.model.Word
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

class WordDataSource @Inject constructor(private val application: Context) {

    fun getWordsFromFile(documentUri: Uri): Result<Collection<Word>, Exception> {

        return Result.of {
            val wordsMap = linkedMapOf<String, Int>()
            val documentText = getTextFromDocument(documentUri)
            if (documentText.isNotEmpty()) {

                val words = documentText.split(" ")
                for (word in words) {
                    val validWord = getWordIfItsValid(word)
                    if (validWord != null) {
                        if (wordsMap.containsKey(validWord)) {
                            wordsMap[validWord] = wordsMap[validWord]?.plus(1) ?: 1
                        } else {
                            wordsMap[validWord] = 1
                        }
                    }
                }
            }
            wordsMap.mapToWordList()
        }
    }

    private fun getTextFromDocument(documentUri: Uri): String {
        var documentText = ""
        val documentInputStream = application.contentResolver.openInputStream(documentUri)
        documentInputStream?.let {
            val inputStreamReader =
                InputStreamReader(documentInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val sb = StringBuilder()
            var s: String?
            while (bufferedReader.readLine().also { s = it } != null) {
                sb.append(s)
                sb.append(" ")
            }
            documentText = sb.toString()
        }
        return documentText
    }

    private fun getWordIfItsValid(word: String): String? {

        val digit: Pattern = Pattern.compile("[0-9]")
        val special: Pattern = Pattern.compile("[\",:.;·/¿?!ªº\'^´¨@#$%&*()_+=|<>{}\\[\\]~-]")

        val wordToCheck = getWordWithoutLastSpecialCharacterIfContains(word)

        val hasDigit: Matcher = digit.matcher(wordToCheck)
        val hasSpecial: Matcher = special.matcher(wordToCheck)
        val isValid = !hasDigit.find() && !hasSpecial.find()

        return if (isValid && word.isNotBlank()) {
            wordToCheck.toLowerCase()
        } else {
            null
        }
    }

    private fun getWordWithoutLastSpecialCharacterIfContains(word: String): String {
        val digit: Pattern = Pattern.compile("[0-9]")
        val special: Pattern = Pattern.compile("[\",:.;·/¿?!ªº\'^´¨@#$%&*()_+=|<>{}\\[\\]~-]")

        var wordToCheck = word
        if (word.length > 1) {
            val lastChar = word.take(word.length - 1)
            val lastCharIsDigit: Matcher = digit.matcher(lastChar)
            val lastCharIsSpecial: Matcher = special.matcher(lastChar)
            wordToCheck = if (lastCharIsDigit.find() || lastCharIsSpecial.find()) {
                word.substring(0, word.length - 1)
            } else {
                word
            }
        }
        return wordToCheck
    }

    private fun Map<String, Int>.mapToWordList(): List<Word> {

        val wordList = mutableListOf<Word>()
        for (key in this.keys) {
            wordList.add(Word(key, this[key] ?: 1))
        }
        return wordList
    }
}