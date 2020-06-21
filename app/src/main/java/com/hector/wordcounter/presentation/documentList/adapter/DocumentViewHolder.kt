package com.hector.wordcounter.presentation.documentList.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hector.wordcounter.R
import com.hector.wordcounter.domain.model.Document
import kotlinx.android.synthetic.main.document_item_layout.view.*

class DocumentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(document: Document, clickListener: OnDocumentsAdapterListener) {

        itemView.documentName.text = document.name

        val typeIconDrawable = if (document.mimeType == "text/plain") {
            R.drawable.ic_text_type
        } else {
            R.drawable.ic_unknown_type
        }
        itemView.documentTypeIcon.setImageDrawable(
            ContextCompat.getDrawable(
                itemView.context,
                typeIconDrawable
            )
        )

        itemView.setOnClickListener {
            if(document.mimeType == "text/plain") {
                clickListener.onClickTextDocument(document.uri.toString(), document.name)

            } else {
                clickListener.onClickInvalidDocument()
            }
        }
    }
}