package com.example.favorite_cats.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.favorite_cats.FavoriteCat
import com.example.ui.databinding.ItemCatBinding

class FavoriteCatsAdapter(
    private val onItemClicked: (cat: FavoriteCat) -> Unit,
    private val onDeleteFavoritesClicked: (cat: FavoriteCat) -> Unit
) : ListAdapter<FavoriteCat, FavoriteCatsAdapter.ViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCatBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, onItemClicked, onDeleteFavoritesClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(
        private val binding: ItemCatBinding,
        val onItemClicked: (cat: FavoriteCat) -> Unit,
        private val onDeleteFavoritesClicked: (cat: FavoriteCat) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cat: FavoriteCat) {
            itemView.setOnClickListener {
                onItemClicked(cat)
            }
            binding.addDeleteFavoriteImage.setOnClickListener {
                onDeleteFavoritesClicked(cat)
            }
            setBucketIcon()
            Glide.with(binding.catImage)
                .load(cat.url)
                .placeholder(com.example.ui.R.drawable.cat_placeholder)
                .into(binding.catImage)
        }

        private fun setBucketIcon() {
            binding.addDeleteFavoriteImage.setImageDrawable(
                binding.root.resources.getDrawable(
                    com.example.ui.R.drawable.ic_bucket,
                    null
                )
            )
        }
    }


    class DiffUtilCallback : DiffUtil.ItemCallback<FavoriteCat>() {

        override fun areItemsTheSame(oldItem: FavoriteCat, newItem: FavoriteCat): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteCat, newItem: FavoriteCat): Boolean {
            return oldItem == newItem
        }
    }
}
