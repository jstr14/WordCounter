package com.hector.wordcounter.documentList

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.hector.wordcounter.R
import com.hector.wordcounter.models.ListedFile
import kotlinx.android.synthetic.main.fragment_document_list.*

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

    private val viewModel: DocumentListViewModel by viewModels()

    private lateinit var folderUri: Uri

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        folderUri = arguments?.getString(FOLDER_URI)?.toUri()
            ?: throw IllegalArgumentException("Must pass URI of directory to open")

        initObservers()

        viewModel.onLoadFiles(folderUri)
    }


    private fun initObservers() {

        viewModel.documents.observe(viewLifecycleOwner, Observer { state ->

            when (state) {
                is DocumentListState.Loading -> {
                    renderLoading()
                }
                is DocumentListState.Error -> {
                    renderErrorMessage()
                }
                is DocumentListState.Success -> {

                    renderSuccessState(state.listedFileList)
                }
            }

        })
    }

    private fun renderLoading() {

        progressBar?.visibility = View.VISIBLE
    }

    private fun renderErrorMessage() {

        progressBar?.visibility = View.GONE
        errorMessage?.visibility = View.VISIBLE

    }

    private fun renderSuccessState(listedFileList: List<ListedFile>) {

        progressBar?.visibility = View.GONE
    }

}
