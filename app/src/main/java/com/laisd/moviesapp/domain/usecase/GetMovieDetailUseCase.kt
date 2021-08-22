package com.laisd.moviesapp.domain.usecase

import com.laisd.moviesapp.BuildConfig.apiKey
import com.laisd.moviesapp.domain.model.MovieDetail
import com.laisd.moviesapp.domain.repository.MovieRepository
import com.laisd.moviesapp.domain.usecase.base.SingleByIdUseCase
import io.reactivex.rxjava3.core.Single

class GetMovieDetailUseCase(private val movieRepository: MovieRepository): SingleByIdUseCase<MovieDetail> {

    override fun execute(movieId: Int): Single<MovieDetail> =
        movieRepository.getMovieDetail(movieId.toString(), apiKey, "pt-BR", "releases,credits")
}