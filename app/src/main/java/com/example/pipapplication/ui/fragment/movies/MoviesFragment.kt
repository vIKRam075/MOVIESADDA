package com.example.pipapplication.ui.fragment.movies

import android.R
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pipapplication.MoviesApp
import com.example.pipapplication.data.Status
import com.example.pipapplication.data.interfaces.onMovieClickListener
import com.example.pipapplication.data.model.FavouriteMoviesResponse
import com.example.pipapplication.data.model.MoviesResponse
import com.example.pipapplication.data.viewmodels.MoviesMainViewModel
import com.example.pipapplication.data.viewmodels.MoviesViewModelFactory
import com.example.pipapplication.databinding.FragmentMoviesBinding
import com.example.pipapplication.ui.activity.MovieDetailsActivity
import com.example.pipapplication.ui.adapter.MoviesAdapter
import com.example.pipapplication.utils.CommonMethods
import com.example.pipapplication.utils.Loader
import com.google.android.material.chip.Chip
import javax.inject.Inject


class MoviesFragment : Fragment(), onMovieClickListener {

    private var _binding: FragmentMoviesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: MoviesAdapter

    private lateinit var moviesMainViewModel: MoviesMainViewModel

    @Inject
    lateinit var moviesViewModelFactory: MoviesViewModelFactory

//    @Inject
//    lateinit var moviesDatabase: MoviesDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initView()
        return root
    }

    private fun initView() {
        (requireActivity().application as MoviesApp).applicationComponent.inject(this)

        moviesMainViewModel =
            ViewModelProvider(this, moviesViewModelFactory).get(MoviesMainViewModel::class.java)
        binding.recyclerMovies.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = MoviesAdapter(this)
        binding.recyclerMovies.hasFixedSize()
        binding.recyclerMovies.adapter = adapter

//        if ((activity as MainActivity).internetAlert()) {
//
        // fetch movies from api
/*        moviesMainViewModel.moviesLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            if (adapter.itemCount > 0) {
                binding.textHome.visibility = View.VISIBLE
            } else {
                binding.textHome.visibility = View.GONE
            }
        }*/
/*        } else {

            // fetch movies from room db
            if (adapter.itemCount > 0) {
                binding.textHome.visibility = View.VISIBLE
            } else {
                binding.textHome.visibility = View.GONE
            }
        }*/
        getMovies()

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.toString().isNullOrEmpty()) {
                    adapter?.filter?.filter("")
                } else {
                    adapter?.filter?.filter(s)
                }

            }
        })

        binding.imgMenu.setOnClickListener {
//            showGenrePopup()
        }

        createGenre()
    }

    override fun onResume() {
        super.onResume()
        getMovies()
    }

    private fun getMovies() {
        if (CommonMethods.isDeviceOnlineStatus(requireContext())) {
            moviesMainViewModel.getMovies().observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.LOADING -> Loader.showLoading(requireContext())
                    Status.SUCCESS -> {
                        moviesMainViewModel.addMovies(it.data!!)
                        bindMoviesListFromRoom()
//                        Loader.stopLoading()
                    }
                    Status.API_ERROR -> {
                        it.message?.let { message ->
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        }
                        //stop loader
                        Loader.stopLoading()
                    }

                    Status.HTTP_ERROR -> {
                        //stop loader
                        Loader.stopLoading()
                        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            }
        } else {
            bindMoviesListFromRoom()
        }
    }

    private fun bindMoviesListFromRoom() {
        moviesMainViewModel.moviesList.observe(viewLifecycleOwner) { movies ->
//            adapter.submitList(movies)
            adapter.setMovies(movies)
            if (adapter.itemCount > 0) {
//                binding.textHome.visibility = View.VISIBLE
            } else {
                binding.textHome.visibility = View.GONE
            }
            Loader.stopLoading()
        }
    }

    private fun createGenre() {
        val genreList = arrayOf(
            "Action", "Adventure", "Animation", "Comedy",
            "Crime", "Drama", "Horror", "Mystery", "Romance", "Sci-Fi", "Thriller"
        )

        for (i in 0 until genreList.size) {
            val chip = Chip(requireContext())
            chip.id = i
            chip.text = genreList[i]
            chip.setChipBackgroundColorResource(R.color.holo_purple)
//            chip.isCloseIconVisible = true
            chip.setTextColor(resources.getColor(R.color.white))
//            chip.setTextAppearance(R.style.ChipTextAppearance)
            chip.isCheckable = true

            chip.setOnCheckedChangeListener { compoundButton, b ->
               /* Toast.makeText(requireContext(), "" + compoundButton.text, Toast.LENGTH_SHORT)
                    .show()*/
                if (b) {
                    adapter?.filter?.filter(compoundButton.text.toString())
                } else {
                    adapter?.filter?.filter("")
                }
            }
            binding.chipGroupMovie.addView(chip)
        }

/*
        binding.chipGroupMovie.setOnCheckedChangeListener { group, checkedIds ->
            val chip: Chip? = group.findViewById(checkedIds)
            chip?.let { chipView ->
            }

        }
        */
    }

    private fun showGenrePopup() {
        val popupMenu = PopupMenu(requireContext(), binding.imgMenu)
        val genreList = arrayOf(
            "Action", "Adventure", "Animation", "Comedy",
            "Crime", "Action", "Drama", "Horror", "Mystery", "Romance", "Sci-Fi", "Thriller"
        )
        for (i in 0 until genreList.size) {
            popupMenu.menu.add(Menu.NONE, i, i, genreList[i])
        }

/*        popupMenu.menu.add(Menu.NONE, 0, 0, "Action")
        popupMenu.menu.add(Menu.NONE, 0, 0, "Adventure")
        popupMenu.menu.add(Menu.NONE, 0, 0, "Animation")
        popupMenu.menu.add(Menu.NONE, 0, 0, "Comedy")
        popupMenu.menu.add(Menu.NONE, 0, 0, "Crime")
        popupMenu.menu.add(Menu.NONE, 0, 0, "Action")
        popupMenu.menu.add(Menu.NONE, 1, 1, "Drama")
        popupMenu.menu.add(Menu.NONE, 0, 0, "Horror")
        popupMenu.menu.add(Menu.NONE, 0, 0, "Mystery")
        popupMenu.menu.add(Menu.NONE, 0, 0, "Romance")
        popupMenu.menu.add(Menu.NONE, 0, 0, "Sci-Fi")
        popupMenu.menu.add(Menu.NONE, 0, 0, "Thriller")*/

        popupMenu.gravity = Gravity.CENTER_HORIZONTAL

        popupMenu.setOnMenuItemClickListener {
/*            when (it.itemId) {
                0 -> {
                }
                1 -> {

                }
            }*/
            adapter?.filter?.filter(genreList[it.itemId])
            true
        }
        popupMenu.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMovieClick(moviesResponse: MoviesResponse) {
        val intent = Intent(requireActivity(), MovieDetailsActivity::class.java)
        intent.putExtra("movie", moviesResponse)
        startActivity(intent)
    }

    override fun onFavMovieClick(favouriteMoviesResponse: FavouriteMoviesResponse) {

    }
}