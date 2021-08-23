package com.laisd.moviesapp.presentation.moviedetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.laisd.moviesapp.R
import com.laisd.moviesapp.databinding.FragmentMovieDetailsBinding
import com.laisd.moviesapp.domain.model.Movie
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

        sharedViewModel.movieDetailError.observe(viewLifecycleOwner) { error ->
            if (error) navigateToError()
        }

        binding.ibBack.setOnClickListener {
            activity?.onBackPressed()
        }

        sharedViewModel.favoriteMovies.observe(viewLifecycleOwner) {
            sharedViewModel.movieDetail.observe(viewLifecycleOwner) { movieDetail ->
                setHeartIcon(args.movieId)
                setMovieInfo(movieDetail!!)
                binding.rvCastMembers.adapter = CastMembersAdapter(movieDetail.cast)
            }
            sharedViewModel.setMovieDetail(args.movieId)
        }

        binding.ibMovieDetailFavorite.setOnClickListener {
            favoriteClicked(args.movieId)
        }
    }

    private fun favoriteClicked(movieId: Int) {
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