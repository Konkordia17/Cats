package com.example.cats_list.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cats_list.Cat
import com.example.ui.databinding.ItemCatBinding

class CatsAdapter(
    private val onItemClicked: (cat: Cat) -> Unit,
    private val isAddToFavoritesClicked: (cat: Cat) -> Unit
) :
    ListAdapter<Cat, CatsAdapter.ViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCatBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, onItemClicked, isAddToFavoritesClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(
        private val binding: ItemCatBinding,
        val onItemClicked: (cat: Cat) -> Unit,
        private val isAddToFavoritesClicked: (cat: Cat) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cat: Cat) {
            itemView.setOnClickListener {
                onItemClicked(cat)
            }
            binding.addDeleteFavoriteImage.setOnClickListener {
                isAddToFavoritesClicked(cat)
            }
            setFavoritesIcon(cat.isFavorite)
            Glide.with(binding.catImage)
                .load(cat.url)
                .placeholder(com.example.ui.R.drawable.cat_placeholder)
                .into(binding.catImage)
        }

        private fun setFavoritesIcon(isFavorite: Boolean) {
            when (isFavorite) {
                true -> binding.addDeleteFavoriteImage.setImageDrawable(
                    binding.root.resources.getDrawable(
                        com.example.ui.R.drawable.red_heart_icon,
                        null
                    )
                )

                false -> binding.addDeleteFavoriteImage.setImageDrawable(
                    binding.root.resources.getDrawable(
                        com.example.ui.R.drawable.heart_icon,
                        null
                    )
                )
            }
        }
    }


    class DiffUtilCallback : DiffUtil.ItemCallback<Cat>() {

        override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem == newItem
        }
    }
}