package com.hector.wordcounter.presentation.documentDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hector.wordcounter.R
import com.hector.wordcounter.domain.model.Word
import javax.inject.Inject

class WordsAdapter @Inject constructor() :
    RecyclerView.Adapter<WordViewHolder>() {

    var wordList: List<Word> = emptyList()

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(wordList[position])
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.word_item_layout, parent, false)
        return WordViewHolder(view)
    }
}
