package com.laisd.moviesapp.presentation.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.laisd.moviesapp.R
import com.laisd.moviesapp.databinding.FragmentMovieDetailsBinding
import com.laisd.moviesapp.domain.model.MovieDetail
import com.laisd.moviesapp.presentation.SharedViewModel
import com.laisd.moviesapp.presentation.moviedetails.adapter.CastMembersAdapter
import com.laisd.moviesapp.presentation.moviedetails.adapter.MovieGenresAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel by sharedViewModel<SharedViewModel>()
    private lateinit var args: MovieDetailsFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args = MovieDetailsFragmentArgs.fromBundle(requireArguments())

        sharedViewModel.initializeLists()

        sharedViewModel.movieDetailError.observe(viewLifecycleOwner) { if (it) setError() }

        binding.ibBack.setOnClickListener { activity?.onBackPressed() }

        sharedViewModel.favoriteMovies.observe(viewLifecycleOwner) {
            sharedViewModel.movieDetail.observe(viewLifecycleOwner) { movieDetail ->
                setHeartIcon(args.movieId, binding.ibMovieDetailFavorite)
                setMovieInfo(movieDetail)
                binding.rvCastMembers.adapter = CastMembersAdapter(movieDetail.cast)
            }
            sharedViewModel.setMovieDetail(args.movieId)
        }

        binding.ibMovieDetailFavorite.setOnClickListener { favoriteListener(args.movieId) }
    }

    private fun setError() {
        binding.constraintMovieDetail.visibility = View.GONE
        binding.constraintMovieDetailError.visibility = View.VISIBLE
        binding.ibErrorClose.setOnClickListener { activity?.onBackPressed() }
        binding.btnErrorTryAgain.setOnClickListener { activity?.onBackPressed() }
    }

    private fun favoriteListener(movieId: Int) {
        sharedViewModel.movieFoundByGenreFilter(args.genre)
        sharedViewModel.movieFoundBySearchMode(args.searchMode)

        sharedViewModel.favoriteClicked(movieId)
        if (sharedViewModel.movieIsFavorite(movieId)) {
            sendToast(getString(R.string.removido))
        } else {
            sendToast(getString(R.string.adicionado))
        }
    }

    private fun sendToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setMovieInfo(movieDetail: MovieDetail) {
        binding.tvMovieDetailRating.text = movieDetail.userRating
        binding.tvMovieDetailTitle.text = movieDetail.title
        binding.tvMovieDetailYear.text = movieDetail.releaseDate
        binding.tvMovieDetailPg.text = movieDetail.filmCertification
        binding.tvMovieDetailRuntime.text = movieDetail.runtime
        binding.tvMovieDetailSynopsis.text = movieDetail.synopsis
        setBackdropPoster(movieDetail, binding.ivMovieDetailPoster)
        binding.rvMovieDetailGenres.adapter = MovieGenresAdapter(movieDetail.genres)
    }

    private fun setHeartIcon(movieId: Int, imageButton: ImageButton) {
        if (sharedViewModel.movieIsFavorite(movieId)) {
            imageButton.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            imageButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    private fun setBackdropPoster(movieDetail: MovieDetail, imageView: ImageView) {
        val imageBaseUrl = "https://image.tmdb.org/t/p/w500/"
        var pictureUrl: String? = null
        movieDetail.backdropPoster?.let { pictureUrl = imageBaseUrl + it }
        Glide.with(this)
            .load(pictureUrl)
            .fallback(R.drawable.ic_baseline_android_24)
            .centerCrop()
            .into(imageView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}