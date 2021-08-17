package com.laisd.moviesapp.domain.usecase

import com.laisd.moviesapp.BuildConfig
import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Single

class SearchMovieUseCase (private val movieRepository: MovieRepository) {

    fun execute(query: String): Single<List<Movie>> =
        movieRepository.searchMovie(BuildConfig.apiKey, "pt-BR", query)

}