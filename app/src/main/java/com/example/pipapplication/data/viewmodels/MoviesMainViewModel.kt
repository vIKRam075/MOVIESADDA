package com.example.pipapplication.data.viewmodels

import androidx.lifecycle.*
import com.example.pipapplication.data.Wrapper
import com.example.pipapplication.data.model.FavouriteMoviesResponse
import com.example.pipapplication.data.model.MoviesResponse
import com.example.pipapplication.data.model.UserModel
import com.example.pipapplication.data.repositories.MoviesRepository
import kotlinx.coroutines.launch

class MoviesMainViewModel(private val repository: MoviesRepository) : ViewModel() {

    //get movies list after storing from api response
    val moviesList: LiveData<MutableList<MoviesResponse>> = repository.moviesList.asLiveData()

    //get favourite movies list
    val favouriteMoviesList: LiveData<MutableList<FavouriteMoviesResponse>> =
        repository.favouriteMoviesList.asLiveData()


    /*val moviesLiveData: LiveData<List<MoviesResponse>>
        get() = repository.movies

    init {
        viewModelScope.launch {
            try {
                repository.getMoviesList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }*/


    fun createUser(userModel: UserModel) = viewModelScope.launch {
        repository.createUser(userModel)
    }

    fun userExists(email: String) = viewModelScope.launch {
        repository.userExists(email)
    }

    fun getMovies() = liveData {
        emit(Wrapper.loading())
        emit(repository.getMovies())
    }

    fun addMovies(movies: List<MoviesResponse>) = viewModelScope.launch {
        repository.addMovies(movies)
    }

    fun addFavouriteMovie(movie: FavouriteMoviesResponse) = viewModelScope.launch {
        repository.addFavouriteMovie(movie)
    }

    fun removeFromFavouriteMovie(favouriteMoviesResponse: FavouriteMoviesResponse) = viewModelScope.launch {
        repository.removeFromFavouriteMovie(favouriteMoviesResponse)
    }

    fun checkFavouriteMovie(id: Int, name: String, username: String): Boolean {
        var isFav = false
        viewModelScope.launch {
            isFav = repository.checkFavouriteMovie(id, name, username)
        }
        return isFav
    }

    fun getUser(email: String,pass:String) = repository.getUser(email,pass)

    fun getUserForRegister(email: String,username:String) = repository.getUserForRegister(email,username)

    fun getFavMovie(mId: Int, mName: String, mUserName: String) = repository.getFavMovie(mId, mName, mUserName)
}