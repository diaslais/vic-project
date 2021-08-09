package com.laisd.moviesapp.presentation.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.laisd.moviesapp.databinding.FragmentMovieDetailsBinding
import com.laisd.moviesapp.domain.model.MovieDetail
import com.laisd.moviesapp.presentation.SharedViewModel
import com.laisd.moviesapp.presentation.moviedetails.adapter.CastMembersAdapter
import com.laisd.moviesapp.presentation.moviedetails.adapter.MovieGenresAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel by viewModel<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = MovieDetailsFragmentArgs.fromBundle(requireArguments())

        sharedViewModel.setMovieDetail(args.movieId)
        sharedViewModel.movieDetail.observe(viewLifecycleOwner, Observer {
            setMovieInfo(it)
        })

        val genresRecyclerView = binding.rvMovieDetailGenres
        sharedViewModel.genresList.observe(viewLifecycleOwner, Observer { genresList ->
            genresRecyclerView.adapter = MovieGenresAdapter(genresList)
        })

        val castRecyclerView = binding.rvCastMembers
        sharedViewModel.movieDetail.observe(viewLifecycleOwner, Observer { movieDetail ->
            castRecyclerView.adapter = CastMembersAdapter(movieDetail.cast)
        })
    }

    private fun setMovieInfo(movieDetail: MovieDetail) {
        binding.tvMovieDetailRating.text = movieDetail.userRating.toString()
        binding.tvMovieDetailTitle.text = movieDetail.title
        binding.tvMovieDetailYear.text = movieDetail.releaseDate
        binding.tvMovieDetailPg.text = movieDetail.filmCertification
        binding.tvMovieDetailRuntime.text = movieDetail.runtime.toString()
        binding.tvMovieDetailSynopsis.text = movieDetail.synopsis
        sharedViewModel.setHeartIcon(binding.ibMovieDetailFavorite, movieDetail)
        sharedViewModel.setBackdropPoster(this, movieDetail, binding.ivMovieDetailPoster)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}