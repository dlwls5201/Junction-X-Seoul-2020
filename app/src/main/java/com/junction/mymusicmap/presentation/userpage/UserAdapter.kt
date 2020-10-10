package com.junction.mymusicmap.presentation.userpage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.junction.mymusicmap.R
import com.junction.mymusicmap.databinding.ItemUserMusicBinding
import kotlinx.android.synthetic.main.item_user_music.view.*

class UserAdapter(private val items: ArrayList<MusicItem>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_user_music,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.itemView.btnMusic.setOnClickListener {

        }
        holder.itemView.btnPin.setOnClickListener {

        }
        holder.apply {
            itemView.tag = item
            bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val binding: ItemUserMusicBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MusicItem) {
            binding.apply {
                model = item
            }
        }
    }
}