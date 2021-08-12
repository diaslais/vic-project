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
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel by viewModel<SharedViewModel>()
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var titles: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.getPopularMovies()
        sharedViewModel.getFavoriteMovies()

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

    private fun setMoviesAndFavorites() {
        sharedViewModel.popularMovies.observe(viewLifecycleOwner) {
            viewPagerAdapter.dataSet[0] = it
            viewPagerAdapter.notifyDataSetChanged()
        }

        sharedViewModel.favoriteMovies.observe(viewLifecycleOwner) {
            viewPagerAdapter.dataSet[1] = it
            viewPagerAdapter.notifyDataSetChanged()
        }
    }

    private fun genresRecyclerView() {
        val genresAdapter =
            GenresFilterAdapter(requireContext(), ::setMoviesAndFavorites, ::genreFilter)
        binding.rvGenres.adapter = genresAdapter

        sharedViewModel.allGenres.observe(viewLifecycleOwner) {
            sharedViewModel.genreTitles().observe(viewLifecycleOwner) { genreTitles ->
                genresAdapter.genresList = genreTitles
                genresAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun movieSearch(query: String) {
        sharedViewModel.searchMovieFromApi(query, binding.ivSearchNotFound, binding.vpMovies)
        sharedViewModel.searchFromApi.observe(viewLifecycleOwner) { filteredList ->
            viewPagerAdapter.dataSet[0] = filteredList
            viewPagerAdapter.notifyDataSetChanged()
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            movieSearch(query)
        }
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            movieSearch(query)
            binding.tabLayout.visibility = View.INVISIBLE
            binding.vpMovies.isUserInputEnabled = false
            binding.ivGreenView.visibility = View.VISIBLE
            binding.tvSearchMode.visibility = View.VISIBLE
            binding.tvBack.visibility = View.VISIBLE

        }
        if (query.isNullOrEmpty()) {
            binding.tabLayout.visibility = View.VISIBLE
            binding.vpMovies.isUserInputEnabled = true
            binding.ivGreenView.visibility = View.INVISIBLE
            binding.tvSearchMode.visibility = View.INVISIBLE
            binding.tvBack.visibility = View.INVISIBLE
            setMoviesAndFavorites()
        }
        return false
    }

    private fun genreFilter(genre: String) {
        sharedViewModel.filterByGenreFromApi(genre).observe(viewLifecycleOwner) {
            viewPagerAdapter.dataSet[0] = it
            viewPagerAdapter.notifyDataSetChanged()
        }

        sharedViewModel.filterByGenreFromFavorites(genre).observe(viewLifecycleOwner) {
            viewPagerAdapter.dataSet[1] = it
            viewPagerAdapter.notifyDataSetChanged()
        }
    }

    private fun favoriteClick(movie: Movie) {
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
            MainScreenFragmentDirections.actionMainScreenFragmentToMovieDetailsFragment(movieId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}