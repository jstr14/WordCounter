package com.hector.wordcounter.presentation.documentList

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hector.wordcounter.R
import com.hector.wordcounter.domain.model.Document
import com.hector.wordcounter.presentation.documentList.adapter.DocumentsAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_document_list.*
import javax.inject.Inject


class DocumentListFragment : DaggerFragment() {

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

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: DocumentListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(DocumentListViewModel::class.java)
    }

    @Inject
    lateinit var documentsAdapter: DocumentsAdapter
    private lateinit var folderUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_document_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        folderUri = arguments?.getString(FOLDER_URI)?.toUri()
            ?: throw IllegalArgumentException("Must pass URI of directory to open")

        initObservers()
        setUpRecyclerView()

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

                    renderSuccessState(state.documentList)
                }
            }

        })
    }

    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(
            requireContext(),
            layoutManager.orientation
        )
        documentList?.apply {
            this.layoutManager = layoutManager
            this.adapter = documentsAdapter
            this.addItemDecoration(dividerItemDecoration)
        }

    }

    private fun renderLoading() {

        progressBar?.visibility = View.VISIBLE
    }

    private fun renderErrorMessage() {

        progressBar?.visibility = View.GONE
        errorMessage?.visibility = View.VISIBLE

    }

    private fun renderSuccessState(documentList: List<Document>) {

        progressBar?.visibility = View.GONE
        documentsAdapter.documentList = documentList
        documentsAdapter.notifyDataSetChanged()

    }

}
