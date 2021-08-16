package com.laisd.moviesapp.presentation.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.laisd.moviesapp.R
import com.laisd.moviesapp.databinding.FragmentMainScreenBinding
import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.presentation.SharedViewModel
import com.laisd.moviesapp.presentation.mainscreen.adapter.GenresFilterAdapter
import com.laisd.moviesapp.presentation.mainscreen.adapter.ViewPagerAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainScreenFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel by sharedViewModel<SharedViewModel>()
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var titles: List<String>
    private lateinit var genresAdapter: GenresFilterAdapter
    private var searchMode = false
    private var selectedGenre: String? = null
    private var selectedGenrePosition: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        genresAdapter =
            GenresFilterAdapter(requireContext(), selectedGenrePosition, ::genreClickListener)

        if (!searchMode) {
            sharedViewModel.initializeLists()
        }

        setSearchView(binding.svSearchMovie)
        setViewPager()
        genresRecyclerView()

        binding.tvBack.setOnClickListener {
            binding.svSearchMovie.let {
                it.setQuery("", true)
                it.clearFocus()
            }
        }
    }

    private fun setSearchView(searchView: SearchView) {
        searchView.let {
            it.setOnQueryTextListener(this)
            it.isFocusable = false
            it.isIconified = false
            it.clearFocus()
        }
    }

    private fun setViewPager() {
        viewPagerAdapter = ViewPagerAdapter(
            sharedViewModel::movieIsFavorite,
            ::favoriteClick,
            ::navigateToMovieDetails
        )
        titles = listOf(getString(R.string.todos_os_filmes), getString(R.string.favoritos))
        binding.vpMovies.adapter = viewPagerAdapter
        setMoviesAndFavorites()
        setTabLayout(titles)
    }

    private fun setTabLayout(titles: List<String>) {
        TabLayoutMediator(binding.tabLayout, binding.vpMovies) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    private fun genreClickListener(genre: String?, position: Int?) {
        selectedGenre = genre
        selectedGenrePosition = position
        if (genre != null) {
            genreFilter(genre)
        } else {
            setMoviesAndFavorites()
            selectedGenrePosition = null
        }
    }

    private fun setMoviesAndFavorites() {
        if (searchMode) {
            sharedViewModel.searchFromApi.observe(viewLifecycleOwner) {
                viewPagerAdapter.dataSet[0] = it
                viewPagerAdapter.notifyDataSetChanged()
            }
        } else if (selectedGenre != null) {
            sharedViewModel.moviesByGenreFromApi.observe(viewLifecycleOwner) {
                viewPagerAdapter.dataSet[0] = it
                viewPagerAdapter.notifyDataSetChanged()
            }
            sharedViewModel.filterByGenreFromApi(selectedGenre!!)
        } else {
            sharedViewModel.popularMovies.observe(viewLifecycleOwner) {
                viewPagerAdapter.dataSet[0] = it
                viewPagerAdapter.notifyDataSetChanged()
            }

            sharedViewModel.favoriteMovies.observe(viewLifecycleOwner) {
                viewPagerAdapter.dataSet[1] = it
                viewPagerAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun genresRecyclerView() {
        binding.rvGenres.adapter = genresAdapter
        sharedViewModel.allGenres.observe(viewLifecycleOwner) {
            sharedViewModel.genreTitles.observe(viewLifecycleOwner) { genreTitles ->
                genresAdapter.genresList = genreTitles
                genresAdapter.notifyDataSetChanged()
            }
            sharedViewModel.genreTitles()
        }
    }

    private fun movieSearch(query: String) {
        sharedViewModel.searchFromApi.observe(viewLifecycleOwner) { filteredList ->
            if (selectedGenre == null) {
                viewPagerAdapter.dataSet[0] = filteredList
                viewPagerAdapter.notifyDataSetChanged()
            } else {
                genreFilter(selectedGenre!!)
            }
        }
        sharedViewModel.searchMovieFromApi(query, binding.ivSearchNotFound, binding.vpMovies)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            movieSearch(query)
        }
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        val searchPlate: View =
            binding.svSearchMovie.findViewById(androidx.appcompat.R.id.search_plate)
        if (query.isNullOrEmpty()) {
            searchMode = false
            setMoviesAndFavorites()
            binding.tabLayout.visibility = View.VISIBLE
            binding.vpMovies.isUserInputEnabled = true
            binding.ivGreenView.visibility = View.INVISIBLE
            binding.tvSearchMode.visibility = View.INVISIBLE
            binding.tvBack.visibility = View.INVISIBLE
            searchPlate.setBackgroundResource(R.drawable.searchview_background)
        } else {
            searchMode = true
            movieSearch(query)
            binding.tabLayout.visibility = View.INVISIBLE
            binding.vpMovies.isUserInputEnabled = false
            binding.ivGreenView.visibility = View.VISIBLE
            binding.tvSearchMode.visibility = View.VISIBLE
            binding.tvBack.visibility = View.VISIBLE
            searchPlate.setBackgroundResource(R.drawable.searchview_background_green)
        }
        return false
    }

    private fun genreFilter(genre: String) {
        if (searchMode) {
            sharedViewModel.moviesByGenreInSearchMode.observe(viewLifecycleOwner) {
                viewPagerAdapter.dataSet[0] = it
                viewPagerAdapter.notifyDataSetChanged()
            }
            sharedViewModel.filterByGenreInSearchMode(genre)
        } else {
            sharedViewModel.moviesByGenreFromApi.observe(viewLifecycleOwner) {
                viewPagerAdapter.dataSet[0] = it
                viewPagerAdapter.notifyDataSetChanged()
            }
            sharedViewModel.filterByGenreFromApi(genre)

            sharedViewModel.moviesByGenreFromFavorites.observe(viewLifecycleOwner) {
                viewPagerAdapter.dataSet[1] = it
                viewPagerAdapter.notifyDataSetChanged()
            }
            sharedViewModel.filterByGenreFromFavorites(genre)
        }
    }

    private fun favoriteClick(movie: Movie) {
        sharedViewModel.movieFoundByGenreFilter(selectedGenre)
        sharedViewModel.movieFoundBySearchMode(searchMode)

        sharedViewModel.favoriteClicked(movie.id)

        if (sharedViewModel.movieIsFavorite(movie.id)) {
            sendToast(getString(R.string.removido))
        } else {
            sendToast(getString(R.string.adicionado))
        }
    }

    private fun sendToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToMovieDetails(movieId: Int) {
        view?.findNavController()?.navigate(
            MainScreenFragmentDirections.actionMainScreenFragmentToMovieDetailsFragment(
                movieId,
                searchMode,
                selectedGenre
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}