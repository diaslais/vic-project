package com.laisd.moviesapp.domain.usecase

import com.laisd.moviesapp.BuildConfig
import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Single

class GetMoviesByGenreUseCase(private val movieRepository: MovieRepository) {

    fun execute(genreId: Int): Single<List<Movie>> =
        movieRepository.getMoviesByGenre(BuildConfig.apiKey, "pt-BR", genreId.toString())
}