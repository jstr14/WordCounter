package com.hector.wordcounter.presentation.documentDetail.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hector.wordcounter.domain.model.Word
import kotlinx.android.synthetic.main.document_item_layout.view.*

class WordViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(word: Word) {
        itemView.documentName.text = word.value
    }
}