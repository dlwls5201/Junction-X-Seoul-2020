package com.junction.mymusicmap.presentation.musicsearch

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.junction.mymusicmap.BR
import com.junction.mymusicmap.R
import com.junction.mymusicmap.data.api.YoutubeMp32Api
import com.junction.mymusicmap.data.api.YoutubeSearchApi
import com.junction.mymusicmap.data.model.YouTubeResponse
import com.junction.mymusicmap.databinding.BottomSheetMusicSearchBinding
import com.junction.mymusicmap.databinding.ItemMusicBinding
import com.tistory.blackjinbase.simplerecyclerview.SimpleRecyclerViewAdapter
import com.tistory.blackjinbase.simplerecyclerview.SimpleViewHolder
import com.tistory.blackjinbase.util.Dlog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.bottom_sheet_music_search.*
import kotlinx.android.synthetic.main.item_music.view.*
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class MusicSearchDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetMusicSearchBinding

    private val compositeDisposable = CompositeDisposable()

    private val youtubeSearchApi by inject<YoutubeSearchApi>(named("search"))

    private val youtubeMp32Api by inject<YoutubeMp32Api>(named("mp32"))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_music_search, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        //val sheetBehavior = BottomSheetBehavior.from(binding.bottomSheetMusicSearch)
        //sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etMusicSearch.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    val query = v.text.toString()
                    Dlog.d("query : $query")

                    if (query.isEmpty()) {
                        return@setOnEditorActionListener true
                    }

                    youtubeSearchApi.searchYoutube(query)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe {
                            showLoading()
                            hideKeyboard()
                        }
                        .doOnTerminate {
                            hideLoading()
                        }
                        .subscribe({ response ->
                            with(binding.rvMusic) {
                                adapter = object :
                                    SimpleRecyclerViewAdapter<YouTubeResponse.Item, ItemMusicBinding>(
                                        R.layout.item_music,
                                        BR.model
                                    ) {
                                    override fun onCreateViewHolder(
                                        parent: ViewGroup,
                                        viewType: Int
                                    ): SimpleViewHolder<ItemMusicBinding> {
                                        return super.onCreateViewHolder(parent, viewType).apply {
                                            itemView.btnPlay.setOnClickListener {
                                                val item = getItem(adapterPosition)
                                                Dlog.d("btnPlay link : ${item.link}")

                                                startActivity(
                                                    Intent(Intent.ACTION_VIEW)
                                                        .setData(Uri.parse(item.link))
                                                        .setPackage("com.google.android.youtube")
                                                )

                                                /*item.link?.let {
                                                    val query = Uri.parse(it).getQueryParameters("v")
                                                    Dlog.d("query : $query}")
                                                    if(query.isNotEmpty()) {
                                                        val videoId = query.first()
                                                        Dlog.d("videoId : $videoId}")
                                                        youtubeMp32Api.getMp3(videoId)
                                                            .subscribe({
                                                                Dlog.d(it.toString())
                                                            }){
                                                                Dlog.e(it.message)
                                                            }
                                                    }
                                                }*/
                                            }

                                            itemView.btnSave.setOnClickListener {
                                                val item = getItem(adapterPosition)
                                                Dlog.d("btnSave item : $item")
                                            }
                                        }
                                    }
                                }.apply {
                                    Dlog.d("items : ${response.items}")
                                    binding.etMusicSearch.setText("")
                                    replaceAll(response.items)
                                }
                            }
                        }) {
                            Dlog.e(it.message)
                        }
                }
            }

            true
        }
    }

    private val audioURL =
        "https://www.android-examples.com/wp-content/uploads/2016/04/Thunder-rumble.mp3"

    private var flag = true

    private var mediaplayer: MediaPlayer? = null

    private fun test() {
        /*binding.btnSample.text = "재생"
        binding.btnSample.setOnClickListener {
            if(flag) {
                mediaplayer = MediaPlayer().apply {
                    setDataSource(audioURL)
                    prepareAsync()
                    setOnPreparedListener {
                        Dlog.d("MyTag","setOnPreparedListener")
                        mediaplayer?.start()
                    }
                }
                binding.btnSample.text = "멈춤"
            } else {
                mediaplayer?.stop()
                mediaplayer = null
                binding.btnSample.text = "재생"
            }

            flag = !flag
        }*/
    }

    override fun onDestroyView() {
        compositeDisposable.dispose()
        super.onDestroyView()
        //mediaplayer?.release()
        //mediaplayer = null
    }

    private fun showLoading() {
        binding.progressLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressLoading.visibility = View.GONE
    }

    private fun hideKeyboard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(etMusicSearch.windowToken, 0)
        etMusicSearch.clearFocus()
    }
}
