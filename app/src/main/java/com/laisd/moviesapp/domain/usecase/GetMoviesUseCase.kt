package com.laisd.moviesapp.domain.usecase

import com.laisd.moviesapp.BuildConfig.apiKey
import com.laisd.moviesapp.domain.model.MoviesList
import com.laisd.moviesapp.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Single

class GetMoviesUseCase(private val movieRepository: MovieRepository) {

    fun execute(): Single<MoviesList> {
        return movieRepository.getMovies(apiKey, "pt-BR")
    }
}
