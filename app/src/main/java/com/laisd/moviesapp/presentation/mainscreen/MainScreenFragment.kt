package com.laisd.moviesapp.presentation.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
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
            ::favoriteListener,
            ::navigateToMovieDetails,
            ::refreshScreen
        )
        binding.vpMovies.adapter = viewPagerAdapter
        titles = listOf(getString(R.string.todos_os_filmes), getString(R.string.favoritos))
        setTabLayout(titles)
        setMoviesAndFavorites()
    }

    private fun setTabLayout(titles: List<String>) {
        TabLayoutMediator(binding.tabLayout, binding.vpMovies) { tab, position ->
            tab.text = titles[position]
        }.attach()
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

    private fun movieSearch(query: String) {
        sharedViewModel.searchFromApi.observe(viewLifecycleOwner) { filteredList ->
            if (selectedGenre == null) {
                viewPagerAdapter.dataSet[0] = filteredList
                viewPagerAdapter.notifyDataSetChanged()
            } else {
                genreFilter(selectedGenre!!)
            }
        }
        sharedViewModel.movieNotFound.observe(viewLifecycleOwner) { movieNotFound ->
            if (movieNotFound) {
                movieNotFound(
                    binding.ivSearchNotFound,
                    binding.vpMovies,
                    binding.tvSearchNotFound,
                    binding.tvSearchNotFoundDescription
                )
            } else {
                movieFound(
                    binding.ivSearchNotFound,
                    binding.vpMovies,
                    binding.tvSearchNotFound,
                    binding.tvSearchNotFoundDescription
                )
            }
        }
        sharedViewModel.searchMovieFromApi(query)
    }

    private fun movieFound(
        imageView: ImageView,
        viewPager2: ViewPager2,
        tv: TextView,
        tvDescription: TextView
    ) {
        viewPager2.visibility = View.VISIBLE
        imageView.visibility = View.GONE
        tv.visibility = View.GONE
        tvDescription.visibility = View.GONE
    }

    private fun movieNotFound(
        imageView: ImageView,
        viewPager2: ViewPager2,
        tv: TextView,
        tvDescription: TextView
    ) {
        viewPager2.visibility = View.INVISIBLE
        imageView.setImageResource(R.drawable.imgsearch)
        imageView.visibility = View.VISIBLE
        tv.text = getString(R.string.filme_nao_existe)
        tv.visibility = View.VISIBLE
        tvDescription.text = getString(R.string.mensagem_nao_encontrado)
        tvDescription.visibility = View.VISIBLE
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            movieSearch(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        val searchPlate: View =
            binding.svSearchMovie.findViewById(androidx.appcompat.R.id.search_plate)
        if (query.isNullOrEmpty()) {
            searchMode = false
            setMoviesAndFavorites()
            sharedViewModel.initializeLists()
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
        return true
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

    private fun favoriteListener(movie: Movie) {
        sharedViewModel.movieFoundByGenreFilter(selectedGenre)
        sharedViewModel.movieFoundBySearchMode(searchMode)

        sharedViewModel.favoriteClicked(movie.id)

        if (sharedViewModel.movieIsFavorite(movie.id)) {
            sendToast(getString(R.string.removido))
        } else {
            sendToast(getString(R.string.adicionado))
        }
    }

    private fun refreshScreen() {
        parentFragmentManager.beginTransaction().detach(this).commitNow()
        parentFragmentManager.beginTransaction().attach(this).commitNow()
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