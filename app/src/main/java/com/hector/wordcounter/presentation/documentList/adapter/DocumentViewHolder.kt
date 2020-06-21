package com.hector.wordcounter.presentation.documentList.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hector.wordcounter.domain.model.Document
import kotlinx.android.synthetic.main.document_item_layout.view.*

class DocumentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(document: Document, clickListener: OnDocumentsAdapterListener) {

        itemView.documentName.text = document.name

        itemView.setOnClickListener {
            clickListener.onClickDocument(document.uri.toString(), document.name)
        }
    }
}