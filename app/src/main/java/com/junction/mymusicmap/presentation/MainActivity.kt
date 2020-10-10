package com.junction.mymusicmap.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.junction.mymusicmap.R
import com.junction.mymusicmap.presentation.musicsearch.MusicSearchDialogFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnMusicSearch.setOnClickListener {
            val bottomSheetFragment = MusicSearchDialogFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
    }
}