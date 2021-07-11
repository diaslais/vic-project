package com.laisd.moviesapp.presentation.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.laisd.moviesapp.databinding.FragmentMainScreenBinding
import com.laisd.moviesapp.presentation.MoviesViewModel

class MainScreenFragment : Fragment() {
    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var moviesViewModel: MoviesViewModel
    private var moviesAdapter = MoviesAdapter()

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

        val genresRecyclerView = binding.rvGenres
        moviesViewModel.genresList.observe(viewLifecycleOwner, Observer { genresList ->
            makeRecyclerView(genresRecyclerView)
            genresRecyclerView.adapter =
                GenresFilterAdapter(
                    genresList
                )
        })

        val moviesViewPager = binding.vpMovies
        moviesViewModel.moviesList.observe(viewLifecycleOwner, Observer { popularMoviesList ->
            moviesAdapter.movieList = popularMoviesList
        })
        makeMoviesViewPager(moviesViewPager)
    }


    private fun makeMoviesViewPager(viewPager2: ViewPager2) {
        viewPager2.adapter = moviesAdapter

        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false

        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        viewPager2.offscreenPageLimit = 3
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(18))
        viewPager2.setPageTransformer(compositePageTransformer)
    }

    private fun makeRecyclerView(recyclerView: RecyclerView) {
        val layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.clipToPadding = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}