package com.laisd.moviesapp.presentation.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.laisd.moviesapp.R
import com.laisd.moviesapp.databinding.FragmentMainScreenBinding
import com.laisd.moviesapp.presentation.ItemListener
import com.laisd.moviesapp.presentation.MoviesViewModel

class MainScreenFragment : Fragment(), ItemListener {
    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var moviesViewModel: MoviesViewModel
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
        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        viewPagerAdapter = ViewPagerAdapter(this, binding.vpMovies)
        setViewPager()
        genresRecyclerView()
    }

    private fun setViewPager() {
        titles = listOf(getString(R.string.todos_os_filmes), getString(R.string.favoritos))

        binding.vpMovies.adapter = viewPagerAdapter

        moviesViewModel.moviesList.observe(viewLifecycleOwner, Observer {
            viewPagerAdapter.dataSet[0] = it
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
        moviesViewModel.genresList.observe(viewLifecycleOwner, Observer { genresList ->
            genresAdapter.genresList = genresList
        })
    }

    override fun onClick(movieId: Int) {
        view?.findNavController()?.navigate(
            MainScreenFragmentDirections.actionMainScreenFragmentToMovieDetailsFragment(
                movieId
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}