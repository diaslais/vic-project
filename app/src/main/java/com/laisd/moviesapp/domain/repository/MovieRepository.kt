package com.laisd.moviesapp.domain.repository

import com.laisd.moviesapp.domain.model.Genre
import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.domain.model.MovieDetail
import io.reactivex.rxjava3.core.Single

/**
 * This interface exists to make an interaction
 * between the domain layer (specifically usecases)
 * and the data layer (repositories implementations)
 *
 * Dependency Inversion Principle - our code should depend on abstractions
 * That way we decouple our code from the lower level implementations
 * **/

interface MovieRepository {

    fun getMovies(apiKey: String, language: String): Single<List<Movie>>

    fun getMovieDetail(movieId: String, apiKey: String, language: String, appendToResponse: String): Single<MovieDetail>

    fun getGenres(apiKey: String, language: String): Single<List<Genre>>

    fun getMoviesByGenre(apiKey: String, language: String, genres: String): Single<List<Movie>>

    fun searchMovie(apiKey: String, language: String, query: String): Single<List<Movie>>

}