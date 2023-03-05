package com.example.pipapplication.di

import android.content.Context
import androidx.room.Room
import com.example.pipapplication.data.database.MoviesDatabase
import com.example.pipapplication.data.database.covertors.ActorConverter
import com.example.pipapplication.data.database.covertors.CreatorConvertor
import com.example.pipapplication.data.database.covertors.DirectorConvertor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideMoviesDB(context: Context): MoviesDatabase {
        return Room.databaseBuilder(context, MoviesDatabase::class.java, "movies_db")
            .addTypeConverter(CreatorConvertor())
            .addTypeConverter(DirectorConvertor())
            .addTypeConverter(ActorConverter())
            .fallbackToDestructiveMigration()
            .build()
    }
}