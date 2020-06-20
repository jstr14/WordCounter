package com.hector.wordcounter.domain.model

enum class WordSortType(val value: String) {
    ORIGINAL_POSITION("position"),
    NUMBER_OF_OCCURRENCES("occurrences"),
    ALPHABETICALLY("alphabetically");

    companion object {
        fun from(value: String): WordSortType =
            values().find { it.value == value } ?: ORIGINAL_POSITION
    }
}