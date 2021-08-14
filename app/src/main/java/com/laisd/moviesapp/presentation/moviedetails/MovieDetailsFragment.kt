package com.laisd.moviesapp.presentation.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.laisd.moviesapp.R
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

        binding.ibBack.setOnClickListener {
            activity?.onBackPressed()
        }

        sharedViewModel.favoriteMovies.observe(viewLifecycleOwner) {
            sharedViewModel.setMovieDetail(args.movieId)

            sharedViewModel.movieDetail.observe(viewLifecycleOwner) { movieDetail ->
                if (movieDetail == null) {
                    navigateToError()
                } else {
                    setHeartIcon(args.movieId)
                    setMovieInfo(movieDetail)
                    binding.rvCastMembers.adapter = CastMembersAdapter(movieDetail.cast)
                }
            }
        }

        binding.ibMovieDetailFavorite.setOnClickListener {
            favoriteClicked(args.movieId)
        }
    }

    private fun favoriteClicked(movieId: Int) {
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
        sharedViewModel.setBackdropPoster(this, movieDetail, binding.ivMovieDetailPoster)
        binding.rvMovieDetailGenres.adapter = MovieGenresAdapter(movieDetail.genres)
    }

    private fun setHeartIcon(movieId: Int) {
        sharedViewModel.setHeartIcon(binding.ibMovieDetailFavorite, movieId)
    }

    private fun navigateToError() {
        view?.findNavController()?.navigate(
            MovieDetailsFragmentDirections.actionMovieDetailsFragmentToErrorFragment()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}