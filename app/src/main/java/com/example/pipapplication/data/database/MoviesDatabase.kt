package com.example.pipapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pipapplication.data.database.covertors.*
import com.example.pipapplication.data.model.*

@Database(entities = [UserModel::class, MoviesResponse::class, FavouriteMoviesResponse::class], version = 1, exportSchema = false)
@TypeConverters(
    CreatorConvertor::class, DirectorConvertor::class,
    AggregateRatingTypeConverters::class, ActorConverter::class,
    GenreConvertor::class, /*TrailerTypeConverters::class*/
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun getMoviesDao(): MoviesDao
}