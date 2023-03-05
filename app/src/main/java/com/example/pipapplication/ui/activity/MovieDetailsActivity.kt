package com.example.pipapplication.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.pipapplication.MoviesApp
import com.example.pipapplication.R
import com.example.pipapplication.data.database.MoviesDatabase
import com.example.pipapplication.data.model.FavouriteMoviesResponse
import com.example.pipapplication.data.model.MoviesResponse
import com.example.pipapplication.data.viewmodels.MoviesMainViewModel
import com.example.pipapplication.data.viewmodels.MoviesViewModelFactory
import com.example.pipapplication.databinding.ActivityDetailsBinding
import com.example.pipapplication.utils.PreferenceManager
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private var moviesResponse: MoviesResponse? = null

    private lateinit var moviesMainViewModel: MoviesMainViewModel

    @Inject
    lateinit var moviesViewModelFactory: MoviesViewModelFactory

    @Inject
    lateinit var moviesDatabase: MoviesDatabase

private var isFav = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {

        moviesResponse = intent.getParcelableExtra<MoviesResponse>("movie")
        binding.movie = moviesResponse
        binding.executePendingBindings()

        (application as MoviesApp).applicationComponent.inject(this)

        moviesMainViewModel =
            ViewModelProvider(this, moviesViewModelFactory).get(MoviesMainViewModel::class.java)

/*        Glide.with(binding.imgMovie).load(moviesResponse?.image)
            .placeholder(R.drawable.img_video_player)
            .dontAnimate().into(binding.imgMovie)*/

        val txtDirector = binding.txtDirector
        txtDirector.text = moviesResponse?.director?.joinToString { it ->
            it?.name + ", "
        }

        val txtCast = binding.txtCast
        txtCast.text = moviesResponse?.actor?.joinToString { it ->
            it?.name + ", "
        }

        val txtGenre = binding.txtGenre
        txtGenre.text = moviesResponse?.genre?.joinToString { it ->
            it.toString() + ", "
        }

        binding.fabFav.setOnClickListener {
            val movie: FavouriteMoviesResponse = FavouriteMoviesResponse(
                moviesResponse?.id!!,
                PreferenceManager.getUsername(),
                moviesResponse?.image,
                moviesResponse?.creator,
                moviesResponse?.keywords,
                moviesResponse?.director,
                moviesResponse?.type,
                moviesResponse?.description,
                moviesResponse?.context,
                moviesResponse?.url,
                moviesResponse?.actor,
                moviesResponse?.datePublished,
                moviesResponse?.duration,
                moviesResponse?.genre,
                moviesResponse?.name,
                moviesResponse?.contentRating,
                moviesResponse?.aggregateRating
            )
            if (isFav) {
                isFav = false
                //fire remove favorite query
                moviesMainViewModel.removeFromFavouriteMovie(movie)
                binding.fabFav.setImageResource(R.drawable.ic_unfav)
            } else {
                isFav = true
                //fire add favorite query
                moviesMainViewModel.addFavouriteMovie(movie)
                binding.fabFav.setImageResource(R.drawable.ic_fav)
            }
        }
        isFavourite(moviesResponse)
    }

    private fun isFavourite(moviesResponse: MoviesResponse?): Boolean {
        moviesMainViewModel.getFavMovie(
            moviesResponse?.id!!,
            moviesResponse?.name!!,
            PreferenceManager.getUsername()!!
        ).observe(this) { movie ->
            if (movie != null) {
                if ((movie.id == moviesResponse?.id)
                    and (movie.name == moviesResponse?.name)
                    and (movie.username == PreferenceManager.getUsername())
                ) {
                    isFav = true
                }
            }

            if (isFav) {
                binding.fabFav.setImageResource(R.drawable.ic_fav)
            } else {
                binding.fabFav.setImageResource(R.drawable.ic_unfav)
            }

        }
/*        val isFav = moviesMainViewModel.checkFavouriteMovie(
            moviesResponse?.id!!,
            moviesResponse?.name!!,
            PreferenceManager.getUsername()!!
        )

        //use this if it works
        *//*
            lifecycleScope.launch {
                val isFav = moviesDatabase.getMoviesDao().checkFavouriteMovie(
                    moviesResponse?.id!!,
                    moviesResponse?.name!!,
                    PreferenceManager.getUsername()!!)
            }*/
        return isFav
    }
}