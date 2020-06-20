package com.hector.wordcounter.presentation.documentDetail

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hector.wordcounter.R
import com.hector.wordcounter.domain.model.Word
import com.hector.wordcounter.domain.model.WordSortType
import com.hector.wordcounter.presentation.documentDetail.adapter.WordsAdapter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_document_detail.*
import javax.inject.Inject


class DocumentDetailActivity : DaggerAppCompatActivity(), AdapterView.OnItemSelectedListener {

    companion object {
        const val DOCUMENT_URI = "DOCUMENT_URI"
    }

    var numberOfSpinnerCalled = 0
    lateinit var menu: Menu
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
        this.menu = menu
        inflater.inflate(R.menu.document_detail_menu, menu)
        val searchViewItem: MenuItem = menu.findItem(R.id.app_bar_search)
        val searchView: SearchView = searchViewItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                /*   if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }*/return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //adapter.getFilter().filter(newText)
                return false
            }
        })
        val spinnerMenuItem = menu.findItem(R.id.filter_list)
        val spinner = spinnerMenuItem.actionView as Spinner


        // set Spinner Adapter
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
        numberOfWords?.text = getString(R.string.totalNumberOfWords, wordList.size)
        wordsAdapter.wordList = wordList
        wordsAdapter.notifyDataSetChanged()

        val searchItem: MenuItem = menu.findItem(R.id.app_bar_search)
        searchItem.isVisible = true
        val spinnerItem: MenuItem = menu.findItem(R.id.filter_list)
        spinnerItem.isVisible = true

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
