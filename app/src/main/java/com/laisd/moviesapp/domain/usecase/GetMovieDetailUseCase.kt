package com.laisd.moviesapp.domain.usecase

import com.laisd.moviesapp.BuildConfig.apiKey
import com.laisd.moviesapp.domain.model.MovieDetail
import com.laisd.moviesapp.domain.repository.MovieDetailRepository
import com.laisd.moviesapp.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Single

class GetMovieDetailUseCase(private val movieDetailRepository: MovieDetailRepository) {
    var movieId = 0

    fun execute(): Single<MovieDetail> {
        return movieDetailRepository.getMovieDetail(movieId, apiKey, "pt-BR", "releases,credits")
    }
}