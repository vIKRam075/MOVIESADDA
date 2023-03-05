package com.example.pipapplication.data.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pipapplication.data.Wrapper
import com.example.pipapplication.data.api.ApiService
import com.example.pipapplication.data.database.MoviesDatabase
import com.example.pipapplication.data.model.FavouriteMoviesResponse
import com.example.pipapplication.data.model.MoviesResponse
import com.example.pipapplication.data.model.UserModel
import com.example.pipapplication.utils.PreferenceManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val apiService: ApiService,
    private val moviesDatabase: MoviesDatabase
) {

    //get users for login
    fun getUser(email: String, pass: String) = moviesDatabase.getMoviesDao().getUser(email, pass)

    //get user for registration
    fun getUserForRegister(email: String, username: String) =
        moviesDatabase.getMoviesDao().getUserForRegister(email, username)

    //get a favorite movie
    fun getFavMovie(mId: Int, mName: String, mUserName: String) =
        moviesDatabase.getMoviesDao().getFavMovie(mId, mName, mUserName)

    //get movies list after storing from api response
    val moviesList: Flow<MutableList<MoviesResponse>> =
        moviesDatabase.getMoviesDao().getMoviesList()

    //get favourite movies list
    val favouriteMoviesList: Flow<MutableList<FavouriteMoviesResponse>> =
        moviesDatabase.getMoviesDao().getFavoriteMovies(PreferenceManager.getUsername().toString())

    /*private val _movies = MutableLiveData<List<MoviesResponse>>()
    val movies: LiveData<List<MoviesResponse>>
        get() = _movies

    suspend fun getMoviesList() {
        val result = apiService.getMovies()
        if (result.isSuccessful && result.body() != null) {
//            if (CommonMethods.isDeviceOnlineStatus(context))
            moviesDatabase.getMoviesDao().addMovies(result.body()!!)
            _movies.postValue(result.body())
        }
    }*/


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun createUser(userModel: UserModel) {
        moviesDatabase.getMoviesDao().createUser(userModel)
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun userExists(email: String) {
        moviesDatabase.getMoviesDao().userExists(email)
    }

    suspend fun getMovies(): Wrapper<List<MoviesResponse>> {
        return try {
            val response = apiService.getMovies()
            when {
                response.isSuccessful -> {
//                    if (response.body()?.code == 200) {
//                    if (response.code() == 200) {
                    Wrapper.success(response.body()!!)
                    /*} else {
                        Wrapper.apiError(
                            response.body(),
                            response.message()
                        )//response.body()?.message)
                    }*/
                }
                else -> {
                    Wrapper.apiError(response.body(), response.code().toString())
                }
            }
        } catch (e: Exception) {
            Wrapper.httpError("${e.message}")
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addMovies(movies: List<MoviesResponse>) {
        moviesDatabase.getMoviesDao().addMovies(movies)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addFavouriteMovie(movie: FavouriteMoviesResponse) {
        moviesDatabase.getMoviesDao().addFavouriteMovie(movie)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun removeFromFavouriteMovie(favouriteMoviesResponse: FavouriteMoviesResponse) {
        moviesDatabase.getMoviesDao().removeFromFavouriteMovie(favouriteMoviesResponse)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun checkFavouriteMovie(id: Int, name: String, username: String): Boolean {
        return moviesDatabase.getMoviesDao().checkFavouriteMovie(id, name, username)
    }
}