package com.example.pipapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pipapplication.R
import com.example.pipapplication.data.interfaces.onMovieClickListener
import com.example.pipapplication.data.model.MoviesResponse
import com.example.pipapplication.databinding.ItemMoviesBinding
import com.example.pipapplication.ui.adapter.MoviesAdapter.MoviesHolder
import java.util.*
import kotlin.collections.ArrayList

class MoviesAdapter(private val onMovieClickListener: onMovieClickListener) :
    ListAdapter<MoviesResponse, MoviesHolder>(MoviesDiffUtil()), Filterable {

    private val _onMovieClickListener: onMovieClickListener = onMovieClickListener

    //    private var moviesFilteredList = ArrayList<MoviesResponse>()
    private var moviesFilteredList = mutableListOf<MoviesResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        return MoviesHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            _onMovieClickListener.onMovieClick(movie)
        }
    }

    fun setMovies(list: MutableList<MoviesResponse>?) {
        this.moviesFilteredList = list!!
        submitList(list)
    }

    //remove if not using filter
    /*override fun getItemCount(): Int {
        return filteredList.size
    }*/

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class MoviesHolder(private val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(moviesResponse: MoviesResponse?) {
            binding.movies = moviesResponse
            binding.executePendingBindings()
/*            Glide.with(binding.imgMovie)
                .load(moviesResponse?.image)
                .placeholder(R.drawable.img_video_player)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imgMovie)*/
//            binding.txtName.text = moviesResponse?.name
//            binding.txtGenre.text = moviesResponse?.genre?.get(0)
/*                moviesResponse?.genre?.joinToString { it -> it.toString() + "," }*/
        }

        companion object {
            fun create(parent: ViewGroup): MoviesHolder {
                val binding =
                    ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MoviesHolder(binding)
            }
        }
    }

    class MoviesDiffUtil : DiffUtil.ItemCallback<MoviesResponse>() {
        override fun areItemsTheSame(oldItem: MoviesResponse, newItem: MoviesResponse): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MoviesResponse, newItem: MoviesResponse): Boolean {
            return oldItem.name == newItem.name
        }
    }

    override fun getFilter(): Filter {
        return moviesFilter
    }

    private val moviesFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<MoviesResponse>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(moviesFilteredList)
            } else {
                for (item in moviesFilteredList) {

                    if (item?.genre?.contains(constraint.toString())!!) {
                        //fetch list for specific category
                        if (item?.genre?.contains(constraint.toString())!!) {
                            filteredList.add(item)
                        }
                    } else {
                        //fetch list according to search string name
                        if (item.name?.toLowerCase()
                                ?.startsWith(constraint.toString().toLowerCase())!!
                        ) {
                            filteredList.add(item)
                        }
                    }


                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
            submitList(filterResults?.values as MutableList<MoviesResponse>)
        }
    }
}