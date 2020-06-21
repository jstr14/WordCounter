package com.hector.wordcounter.presentation.documentDetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hector.wordcounter.R
import com.hector.wordcounter.domain.model.FileInfo
import com.hector.wordcounter.presentation.documentDetail.adapter.WordsAdapter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_document_detail.*
import javax.inject.Inject


class DocumentDetailActivity : DaggerAppCompatActivity(), AdapterView.OnItemSelectedListener {

    companion object {
        const val DOCUMENT_URI = "DOCUMENT_URI"
        const val FILENAME = "FILENAME"
        @JvmStatic
        fun newInstance(documentUri: String, fileName: String?, context: Context) {
            val intent = Intent(context, DocumentDetailActivity::class.java)
            intent.putExtra(DOCUMENT_URI, documentUri)
            intent.putExtra(FILENAME, fileName)
            context.startActivity(intent)
        }
    }

    var numberOfSpinnerCalled = 0
    private var menu: Menu? = null
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


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.document_detail_menu, menu)
        this.menu = menu
        val searchViewItem: MenuItem = menu.findItem(R.id.app_bar_search)
        val searchView: SearchView = searchViewItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                viewModel.queryList(query)
                return false
            }
        })
        val spinnerMenuItem = menu.findItem(R.id.filter_list)
        val spinner = spinnerMenuItem.actionView as Spinner

        val spinnerAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.filter_options,
            R.layout.spinner_item
        )

        spinner.onItemSelectedListener = this
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        return super.onCreateOptionsMenu(menu)
    }

    private fun setUpToolbar() {

        setSupportActionBar(toolbar)
        supportActionBar?.let { actionBar ->
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
            val fileName = intent.extras?.getString(FILENAME)
            fileName?.let {
                title = it
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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
                    renderSuccessState(state.fileInfo)
                }
            }
        })
    }

    private fun renderLoading() {

        progressBar?.visibility = View.VISIBLE
    }

    private fun renderErrorMessage(errorState: DocumentDetailState.Error) {
        progressBar?.visibility = View.GONE
        wordsAdapter.wordList = emptyList()
        wordsAdapter.notifyDataSetChanged()
        val messageToDisplay = when {
            errorState.isEmptyList -> {
                getString(R.string.error_document_empty_words)
            }
            errorState.isErrorAtProcessFile -> {
                getString(R.string.error_document_detail_process)
            }
            else -> {
                getString(R.string.error_query_empty)
            }
        }
        errorMessage.text = messageToDisplay
        errorMessage.visibility = View.VISIBLE
    }

    private fun renderSuccessState(fileInfo: FileInfo) {
        progressBar?.visibility = View.GONE
        errorMessage.visibility = View.GONE
        numberOfWords?.text = getString(R.string.totalNumberOfWords, fileInfo.totalNumberOfWords)
        wordsAdapter.wordList = fileInfo.words.toList()
        wordsAdapter.notifyDataSetChanged()

        val searchItem: MenuItem? = menu?.findItem(R.id.app_bar_search)
        searchItem?.isVisible = true
        val spinnerItem: MenuItem? = menu?.findItem(R.id.filter_list)
        spinnerItem?.isVisible = true

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (++numberOfSpinnerCalled > 1) {
            val value = parent?.getItemAtPosition(position).toString()
            viewModel.sortByType(value)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
