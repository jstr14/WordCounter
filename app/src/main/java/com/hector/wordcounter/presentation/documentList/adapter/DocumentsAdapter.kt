package com.hector.wordcounter.presentation.documentList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hector.wordcounter.R
import com.hector.wordcounter.domain.model.Document
import javax.inject.Inject

class DocumentsAdapter @Inject constructor() :
    RecyclerView.Adapter<DocumentViewHolder>() {

    var documentList: List<Document> = emptyList()


    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        holder.bind(documentList[position])
    }

    override fun getItemCount(): Int {
        return documentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.document_item_layout, parent, false)
        return DocumentViewHolder(view)
    }
}
