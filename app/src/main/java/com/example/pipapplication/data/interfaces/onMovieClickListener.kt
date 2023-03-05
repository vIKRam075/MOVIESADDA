package com.example.pipapplication.data.interfaces

import com.example.pipapplication.data.model.FavouriteMoviesResponse
import com.example.pipapplication.data.model.MoviesResponse

interface onMovieClickListener {
    fun onMovieClick(moviesResponse: MoviesResponse)
    fun onFavMovieClick(favouriteMoviesResponse: FavouriteMoviesResponse)
}