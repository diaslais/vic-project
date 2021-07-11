package com.laisd.moviesapp.presentation.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.laisd.moviesapp.databinding.FragmentMovieDetailsBinding
import com.laisd.moviesapp.presentation.MoviesViewModel

class MovieDetailsFragment : Fragment() {
    lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var moviesViewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)

        val args =
            MovieDetailsFragmentArgs.fromBundle(
                requireArguments()
            )

        setMovieInfo(args)

//        val genresRecyclerView = binding.rvMovieDetailGenres
//        makeRecyclerView(genresRecyclerView)
//        genresRecyclerView.adapter = MovieGenresAdapter(args.movieGenres)

//        val castRecyclerView = binding.rvCastMembers
//        makeRecyclerView(castRecyclerView)
//        castRecyclerView.adapter = CastMembersAdapter(args.movieCast)
    }

    private fun setMovieInfo(args: MovieDetailsFragmentArgs) {
        binding.tvMovieDetailTitle.text = args.movieTitle
    }

    private fun makeRecyclerView(recyclerView: RecyclerView) {
        val layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.clipToPadding = false
    }
}