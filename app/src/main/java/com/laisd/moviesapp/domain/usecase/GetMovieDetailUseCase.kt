package com.laisd.moviesapp.domain.usecase

import com.laisd.moviesapp.BuildConfig.apiKey
import com.laisd.moviesapp.data.repository.MoviesRepositoryImpl
import com.laisd.moviesapp.domain.model.MovieDetail
import com.laisd.moviesapp.domain.repository.MoviesRepository
import io.reactivex.rxjava3.core.Single

class GetMovieDetailUseCase(
    private val moviesRepository: MoviesRepository
) {
    var movieId = 0

    fun execute(): Single<MovieDetail> {
        return moviesRepository.getMovieDetail(movieId, apiKey, "pt-BR", "releases,credits")
    }
}