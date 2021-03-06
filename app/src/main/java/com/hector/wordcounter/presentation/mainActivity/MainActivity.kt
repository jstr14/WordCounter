package com.hector.wordcounter.presentation.mainActivity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import com.hector.wordcounter.R
import com.hector.wordcounter.presentation.documentList.DocumentListFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity() {

    companion object {
        const val FOLDER_SELECTION_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initViews()
        supportFragmentManager.addOnBackStackChangedListener {
            displayProperElements()
        }
    }

    override fun onResume() {
        super.onResume()
        displayProperElements()
    }

    private fun displayProperElements() {
        val someFragmentDisplayed = supportFragmentManager.backStackEntryCount > 0

        supportActionBar?.let { actionBar ->
            actionBar.setDisplayHomeAsUpEnabled(someFragmentDisplayed)
            actionBar.setDisplayShowHomeEnabled(someFragmentDisplayed)
        }
        val mainElementsVisibility = if (someFragmentDisplayed) {
            View.GONE
        } else {
            View.VISIBLE
        }
        mainElementsVisibility.apply {
            selectFolderButton?.visibility = this
            mainMessage?.visibility = this
        }
    }

    private fun initViews() {

        selectFolderButton?.setOnClickListener {
            selectFolder()
        }
    }

    private fun selectFolder() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                    Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
        }
        startActivityForResult(
            intent,
            FOLDER_SELECTION_REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FOLDER_SELECTION_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val directoryUri = data?.data ?: return

            contentResolver.takePersistableUriPermission(
                directoryUri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            navigateToFolderList(directoryUri)
        }
    }

    fun navigateToFolderList(directoryUri: Uri) {
        supportFragmentManager.commit {
            val folderTag = directoryUri.toString()
            val folderFragment = DocumentListFragment.newInstance(directoryUri)
            replace(R.id.fragment_container, folderFragment, folderTag)
            addToBackStack(folderTag)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return false
    }

}
