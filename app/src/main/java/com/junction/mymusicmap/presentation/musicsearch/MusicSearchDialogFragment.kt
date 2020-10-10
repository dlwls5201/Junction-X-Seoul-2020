package com.junction.mymusicmap.presentation.musicsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.junction.mymusicmap.R
import com.junction.mymusicmap.databinding.BottomSheetMusicSearchBinding
import com.tistory.blackjinbase.ext.toast
import com.tistory.blackjinbase.liverecyclerview.bindData
import kotlinx.android.synthetic.main.bottom_sheet_music_search.*

class MusicSearchDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetMusicSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_music_search, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvMusic.bindData(
            listOf(
                "https://i.pinimg.com/736x/43/70/f7/4370f767971012759fe784e90664e10c.jpg",
                "https://i.pinimg.com/736x/43/70/f7/4370f767971012759fe784e90664e10c.jpg",
                "https://img.hani.co.kr/imgdb/resize/2018/0213/151842048519_20180213.JPG"
            ),
            R.layout.item_music,
            viewLifecycleOwner
        ) {
            requireContext().toast(it)
        }
    }
}
