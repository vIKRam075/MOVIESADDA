package com.example.pipapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pipapplication.R
import com.example.pipapplication.data.interfaces.onMovieClickListener
import com.example.pipapplication.data.model.FavouriteMoviesResponse
import com.example.pipapplication.databinding.ItemFavouriteBinding
import com.example.pipapplication.databinding.ItemMoviesBinding

class FavoriteMoviesAdapter(private val onMovieClickListener: onMovieClickListener) :
    ListAdapter<FavouriteMoviesResponse, FavoriteMoviesAdapter.FavoriteMoviesHolder>(
        FavoriteMoviesDiffUtil()
    ) {

    private val _onMovieClickListener: onMovieClickListener = onMovieClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMoviesHolder {
        return FavoriteMoviesHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FavoriteMoviesHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            _onMovieClickListener.onFavMovieClick(movie)
        }
    }


    class FavoriteMoviesHolder(private val binding: ItemFavouriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(moviesResponse: FavouriteMoviesResponse?) {
            binding.movies = moviesResponse
            binding.executePendingBindings()
            /*Glide.with(binding.imgMovie)
                .load(moviesResponse?.image)
                .placeholder(R.drawable.img_video_player)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imgMovie)
            binding.txtName.text = moviesResponse?.name
            binding.txtGenre.text = moviesResponse?.genre?.get(0)*/
/*                moviesResponse?.genre?.joinToString { it -> it.toString() + "," }*/
        }

        companion object {
            fun create(parent: ViewGroup): FavoriteMoviesHolder {
                val binding =
                    ItemFavouriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return FavoriteMoviesHolder(binding)
            }
        }
    }

    class FavoriteMoviesDiffUtil : DiffUtil.ItemCallback<FavouriteMoviesResponse>() {
        override fun areItemsTheSame(
            oldItem: FavouriteMoviesResponse,
            newItem: FavouriteMoviesResponse
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: FavouriteMoviesResponse,
            newItem: FavouriteMoviesResponse
        ): Boolean {
            return oldItem.name == newItem.name
        }
    }
}