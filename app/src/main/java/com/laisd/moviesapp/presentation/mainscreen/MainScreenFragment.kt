package com.laisd.moviesapp.presentation.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.laisd.moviesapp.R
import com.laisd.moviesapp.databinding.FragmentMainScreenBinding
import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.presentation.SharedViewModel
import com.laisd.moviesapp.presentation.mainscreen.adapter.GenresFilterAdapter
import com.laisd.moviesapp.presentation.mainscreen.adapter.ViewPagerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenFragment : Fragment() {
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

        viewPagerAdapter = ViewPagerAdapter(binding.vpMovies, sharedViewModel::movieIsFavorite, ::favoriteClick, ::navigateToMovieDetails)
        setViewPager()
        genresRecyclerView()
    }


    private fun setViewPager() {
        titles = listOf(getString(R.string.todos_os_filmes), getString(R.string.favoritos))
        binding.vpMovies.adapter = viewPagerAdapter

        sharedViewModel.popularMovies.observe(viewLifecycleOwner, Observer {
            viewPagerAdapter.dataSet[0] = it
            viewPagerAdapter.notifyDataSetChanged()
        })

        sharedViewModel.favoriteMovies.observe(viewLifecycleOwner, Observer {
            viewPagerAdapter.dataSet[1] = it
            viewPagerAdapter.notifyDataSetChanged()
        })

        TabLayoutMediator(binding.tabLayout, binding.vpMovies) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    private fun genresRecyclerView() {
        val genresAdapter = GenresFilterAdapter()
        binding.rvGenres.adapter = genresAdapter
        sharedViewModel.genresList.observe(viewLifecycleOwner, Observer { genresList ->
            genresAdapter.genresList = genresList
            genresAdapter.notifyDataSetChanged()
        })
    }

    private fun favoriteClick(movie: Movie) {
        sharedViewModel.favoriteClicked(movie)
        if (sharedViewModel.movieIsFavorite(movie.id)) {
            sendToast("Removido dos favoritos")
        } else {
            sendToast("Adicionado aos favoritos")
        }
    }

    private fun navigateToMovieDetails(movieId: Int) {
        view?.findNavController()?.navigate(
            MainScreenFragmentDirections.actionMainScreenFragmentToMovieDetailsFragment(movieId)
        )
    }

    private fun sendToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}