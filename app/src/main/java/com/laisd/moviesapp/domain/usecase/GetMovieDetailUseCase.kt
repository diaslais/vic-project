package com.laisd.moviesapp.domain.usecase

import com.laisd.moviesapp.BuildConfig.apiKey
import com.laisd.moviesapp.domain.model.MovieDetail
import com.laisd.moviesapp.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Single

class GetMovieDetailUseCase(private val movieRepository: MovieRepository) {

    fun execute(movieId: Int): Single<MovieDetail> =
        movieRepository.getMovieDetail(movieId, apiKey, "pt-BR", "releases,credits")
}