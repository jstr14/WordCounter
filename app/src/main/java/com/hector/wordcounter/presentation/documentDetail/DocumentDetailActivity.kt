package com.hector.wordcounter.presentation.documentDetail

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hector.wordcounter.R
import com.hector.wordcounter.domain.model.Word
import com.hector.wordcounter.presentation.documentDetail.adapter.WordsAdapter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_document_detail.*
import kotlinx.android.synthetic.main.activity_document_detail.errorMessage
import kotlinx.android.synthetic.main.activity_document_detail.progressBar
import javax.inject.Inject

class DocumentDetailActivity : DaggerAppCompatActivity() {

    companion object {
        const val DOCUMENT_URI = "DOCUMENT_URI"
    }

    @Inject
    lateinit var wordsAdapter: WordsAdapter
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: DocumentDetailViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(DocumentDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_detail)

        setUpToolbar()

        val documentUri = intent.extras?.getString(DOCUMENT_URI)?.toUri()
            ?: throw  java.lang.IllegalArgumentException("Must pass URI of document")

        initObservers()
        setUpRecyclerView()
        onViewLoaded(documentUri)
    }

    private fun setUpToolbar() {

        setSupportActionBar(toolbar)
        supportActionBar?.let { actionBar ->
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }
    }

    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(
            this,
            layoutManager.orientation
        )
        wordList?.apply {
            this.layoutManager = layoutManager
            this.adapter = wordsAdapter
            this.addItemDecoration(dividerItemDecoration)
        }

    }

    private fun onViewLoaded(documentUri: Uri) {
        viewModel.getWordOccurrencesForDocument(documentUri)
    }

    private fun initObservers() {
        viewModel.documentDetailState.observe(this, Observer { state ->
            when (state) {
                is DocumentDetailState.Loading -> {
                    renderLoading()
                }
                is DocumentDetailState.Error -> {
                    renderErrorMessage(state)
                }
                is DocumentDetailState.Success -> {
                    renderSuccessState(state.wordList)
                }
            }
        })
    }

    private fun renderLoading() {

        progressBar?.visibility = View.VISIBLE
    }

    private fun renderErrorMessage(errorState: DocumentDetailState.Error) {
        progressBar?.visibility = View.GONE
        val messageToDisplay = if (errorState.isEmptyList) {
            getString(R.string.error_document_empty_words)
        } else {
            getString(R.string.error_document_detail_process)
        }
        errorMessage.text = messageToDisplay
    }

    private fun renderSuccessState(wordList: List<Word>) {
        progressBar?.visibility = View.GONE
        wordsAdapter.wordList = wordList
        wordsAdapter.notifyDataSetChanged()
    }


}
