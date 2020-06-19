package com.hector.wordcounter.documentList

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hector.wordcounter.R


class DocumentListFragment : Fragment(R.layout.fragment_document_list) {

    companion object {

        const val FOLDER_URI = "FOLDER_URI"
        @JvmStatic
        fun newInstance(directoryUri: Uri) =
            DocumentListFragment().apply {
                arguments = Bundle().apply {
                    putString(FOLDER_URI, directoryUri.toString())
                }
            }
    }

    val viewModel: DocumentListViewModel by viewModels()

    private lateinit var folderUri: Uri

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        folderUri = arguments?.getString(FOLDER_URI)?.toUri()
            ?: throw IllegalArgumentException("Must pass URI of directory to open")


        viewModel.onLoadFiles(folderUri)
    }

}
