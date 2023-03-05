package com.example.pipapplication.data.api

import com.example.pipapplication.data.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("top250.json")
    suspend fun getMovies(): Response<List<MoviesResponse>>
}