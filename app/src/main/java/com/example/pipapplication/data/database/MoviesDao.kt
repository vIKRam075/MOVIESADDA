package com.example.pipapplication.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pipapplication.data.model.FavouriteMoviesResponse
import com.example.pipapplication.data.model.MoviesResponse
import com.example.pipapplication.data.model.UserModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(userModel: UserModel)

/*    @Query("SELECT COUNT() FROM UserModel WHERE username = :userEmail")
    suspend fun userLogin(userEmail: String): Int**/

    @Query("SELECT EXISTS (SELECT 1 FROM UserModel WHERE email = :userEmail)")
    suspend fun userExists(userEmail: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(moviesResponse: List<MoviesResponse>)

    @Query("SELECT * FROM MoviesResponse")
    fun getMoviesList(): Flow<MutableList<MoviesResponse>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavouriteMovie(moviesResponse: FavouriteMoviesResponse)

    @Delete
    suspend fun removeFromFavouriteMovie(favouriteMoviesResponse: FavouriteMoviesResponse)

    @Query("SELECT * FROM FavouriteMoviesResponse WHERE username = :userName")
    fun getFavoriteMovies(userName: String): Flow<MutableList<FavouriteMoviesResponse>>

    @Query("SELECT EXISTS (SELECT 1 FROM FavouriteMoviesResponse WHERE id=:mId and name = :mName and username=:mUserName)")
    suspend fun checkFavouriteMovie(mId: Int, mName: String, mUserName: String): Boolean

    @Query("SELECT * FROM FavouriteMoviesResponse WHERE id=:mId and name = :mName and username=:mUserName")
    fun getFavMovie(mId: Int, mName: String, mUserName: String): LiveData<FavouriteMoviesResponse>

    @Query("SELECT * FROM UserModel WHERE email = :userEmail and password = :userPass")
    fun getUser(userEmail: String, userPass: String): LiveData<UserModel?>

    @Query("SELECT * FROM UserModel WHERE email = :userEmail and username = :userName")
    fun getUserForRegister(userEmail: String, userName: String): LiveData<UserModel>
}