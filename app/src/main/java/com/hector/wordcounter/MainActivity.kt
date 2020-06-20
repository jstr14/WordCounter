package com.hector.wordcounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initViews()
    }

    private fun initViews() {

        selectFolderButton?.setOnClickListener {
            Toast.makeText(this,"Select Folder",Toast.LENGTH_LONG).show()
        }
    }

}
