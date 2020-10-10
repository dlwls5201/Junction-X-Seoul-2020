package com.junction.mymusicmap.presentation.userpage

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.junction.mymusicmap.R
import com.junction.mymusicmap.databinding.FragmentUserPageBinding
import com.tistory.blackjinbase.util.Dlog
import kotlinx.android.synthetic.main.fragment_user_page.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserPageFragment : Fragment() {

    private val viewModel: UserPageViewModel by viewModel()

    private lateinit var binding: FragmentUserPageBinding
    private val adapter: UserAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_page, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvUser.adapter = adapter

        binding.rvUser.run {
            adapter = UserAdapter(items = arrayListOf())
            addItemDecoration(VerticalSpaceItemDecoration(8))
            setHasFixedSize(true)
        }

        binding.imgBack.setOnClickListener {

        }

        binding.imgUser.apply {
            background = ShapeDrawable(OvalShape())
            clipToOutline = true
        }

        viewModel.zepetoResponseLiveData.observe(viewLifecycleOwner, Observer {
            Glide.with(this.requireContext()).load(it.url).into(imgUser)
            Dlog.v(it.url.toString())
        })

        Glide.with(this.requireContext()).load(
            viewModel.getUrl()
        ).into(binding.imgUser)
    }
}
