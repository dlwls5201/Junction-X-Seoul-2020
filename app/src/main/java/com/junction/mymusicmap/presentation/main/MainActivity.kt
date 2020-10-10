package com.junction.mymusicmap.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.junction.mymusicmap.R
import com.junction.mymusicmap.presentation.musicsearch.MusicSearchDialogFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    private fun pinClickEvent() {
        val bottomSheetFragment = MusicSearchDialogFragment()
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }
}