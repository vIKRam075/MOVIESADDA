package com.example.pipapplication.ui.fragment.favorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pipapplication.MoviesApp
import com.example.pipapplication.data.database.MoviesDatabase
import com.example.pipapplication.data.interfaces.onMovieClickListener
import com.example.pipapplication.data.model.FavouriteMoviesResponse
import com.example.pipapplication.data.model.MoviesResponse
import com.example.pipapplication.data.viewmodels.MoviesMainViewModel
import com.example.pipapplication.data.viewmodels.MoviesViewModelFactory
import com.example.pipapplication.databinding.FragmentFavoriteBinding
import com.example.pipapplication.ui.activity.FavMovieDetailsActivity
import com.example.pipapplication.ui.activity.MovieDetailsActivity
import com.example.pipapplication.ui.adapter.FavoriteMoviesAdapter
import com.example.pipapplication.ui.adapter.MoviesAdapter
import javax.inject.Inject

class FavoritesFragment : Fragment(),onMovieClickListener {

    private var _binding: FragmentFavoriteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: FavoriteMoviesAdapter

    private lateinit var moviesMainViewModel: MoviesMainViewModel

    @Inject
    lateinit var moviesViewModelFactory: MoviesViewModelFactory

    @Inject
    lateinit var moviesDatabase: MoviesDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initView()
        return root
    }

    private fun initView() {
        (requireActivity().application as MoviesApp).applicationComponent.inject(this)

        moviesMainViewModel =
            ViewModelProvider(this, moviesViewModelFactory).get(MoviesMainViewModel::class.java)
        binding.recyclerMovies.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = FavoriteMoviesAdapter(this)
        binding.recyclerMovies.hasFixedSize()
        binding.recyclerMovies.adapter = adapter

        moviesMainViewModel.favouriteMoviesList.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies)
            if (adapter.itemCount > 0) {
//                binding.textFav.visibility = View.VISIBLE
            } else {
                binding.textFav.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMovieClick(moviesResponse: MoviesResponse) {

    }

    override fun onFavMovieClick(favouriteMoviesResponse: FavouriteMoviesResponse) {
        val intent = Intent(requireActivity(), FavMovieDetailsActivity::class.java)
        intent.putExtra("movie",favouriteMoviesResponse)
        startActivity(intent)
    }

}