package com.junction.mymusicmap.presentation.userpage

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.junction.mymusicmap.R
import com.junction.mymusicmap.databinding.BottomSheetUserPageBinding
import com.tistory.blackjinbase.ext.toPx
import com.tistory.blackjinbase.util.Dlog
import kotlinx.android.synthetic.main.bottom_sheet_music_search.*
import kotlinx.android.synthetic.main.bottom_sheet_user_page.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserPageDialogFragment : BottomSheetDialogFragment() {

    private val viewModel: UserPageViewModel by viewModel()

    private lateinit var binding: BottomSheetUserPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_user_page, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initParentHeight()

        /*viewModel.zepetoResponseLiveData.observe(viewLifecycleOwner, Observer {
            Glide.with(this.requireContext()).load(it.url).into(ivProfile)
            Dlog.v(it.url.toString())
        })

        Glide.with(this.requireContext()).load(
            viewModel.getUrl()
        ).into(binding.ivProfile)*/
    }

    private fun initParentHeight() {
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)

        val deviceHeight: Int = displayMetrics.heightPixels

        val layoutParams = bottomSheetUserPage.layoutParams
        layoutParams.height = deviceHeight - 24.toPx()

        bottomSheetUserPage.layoutParams = layoutParams
    }
}
