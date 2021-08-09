package com.laisd.moviesapp.presentation.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.laisd.moviesapp.R
import com.laisd.moviesapp.databinding.FragmentMovieDetailsBinding
import com.laisd.moviesapp.domain.model.MovieDetail
import com.laisd.moviesapp.presentation.MovieDetailsViewModel
import com.laisd.moviesapp.presentation.moviedetail.adapters.CastMembersAdapter
import com.laisd.moviesapp.presentation.moviedetail.adapters.MovieGenresAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val movieDetailViewModel by viewModel<MovieDetailsViewModel>()

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

        movieDetailViewModel.getMovieDetails(args.movieId).observe(viewLifecycleOwner, Observer {
            setMovieInfo(it)
        })

        val genresRecyclerView = binding.rvMovieDetailGenres
        makeRecyclerView(genresRecyclerView)
        movieDetailViewModel.genresList.observe(viewLifecycleOwner, Observer {
            genresRecyclerView.adapter =
                MovieGenresAdapter(it)
        })

        val castRecyclerView = binding.rvCastMembers
        makeRecyclerView(castRecyclerView)
        movieDetailViewModel.movieDetail.observe(viewLifecycleOwner, Observer {
            castRecyclerView.adapter =
                CastMembersAdapter(it.cast)
        })
    }

    private fun setMovieInfo(movieDetail: MovieDetail) {
        val imageBaseUrl = "https://image.tmdb.org/t/p/w500/"

        binding.tvMovieDetailRating.text = movieDetail.userRating.toString()
        binding.tvMovieDetailTitle.text = movieDetail.title
        binding.tvMovieDetailYear.text = movieDetail.releaseDate
        binding.tvMovieDetailPg.text = movieDetail.filmCertification
        binding.tvMovieDetailRuntime.text = movieDetail.runtime.toString()
        binding.tvMovieDetailSynopsis.text = movieDetail.synopsis

        var pictureUrl: String? = null
        movieDetail.backdropPoster?.let {pictureUrl = imageBaseUrl + it}

        loadImage(pictureUrl, binding.ivMovieDetailPoster)
    }

    private fun loadImage(url: String?, imageView: ImageView) {
        Glide.with(this)
            .load(url)
            .fallback(R.drawable.drive)
            .centerCrop()
            .into(imageView)
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