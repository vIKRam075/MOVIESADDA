package com.example.pipapplication.di

import android.content.Context
import com.example.pipapplication.ui.activity.*
import com.example.pipapplication.ui.fragment.favorites.FavoritesFragment
import com.example.pipapplication.ui.fragment.movies.MoviesFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiClientModule::class, DatabaseModule::class/*, ContextModule::class*/])
interface ApplicationComponent {

    fun inject(registerActivity: RegisterActivity)

    fun inject(loginActivity: LoginActivity)

    fun inject(mainActivity: MainActivity)

    fun inject(moviesFragment: MoviesFragment)

    fun inject(favoritesFragment: FavoritesFragment)

    fun inject(movieDetailsActivity: MovieDetailsActivity)

    fun inject(favMovieDetailsActivity: FavMovieDetailsActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}