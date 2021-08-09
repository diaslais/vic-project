package com.laisd.moviesapp.domain.usecase

import com.laisd.moviesapp.BuildConfig.apiKey
import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Single

class GetMoviesUseCase(private val movieRepository: MovieRepository) {

    fun execute(): Single<List<Movie>> = movieRepository.getMovies(apiKey, "pt-BR")
}
