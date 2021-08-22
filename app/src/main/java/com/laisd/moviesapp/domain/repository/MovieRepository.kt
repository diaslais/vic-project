package com.laisd.moviesapp.domain.repository

import com.laisd.moviesapp.domain.model.Genre
import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.domain.model.MovieDetail
import io.reactivex.rxjava3.core.Single

/**
 * This is how the Domain layer communicates with the Data layer.
 * Domain layer classes (such as usecases) can implement this repository interface instead of having
 * dependencies on other layers, while the repository implementation is in the data layer.
 *
 * Reduce dependency between classes through an interface (abstraction),
 * which brings more flexibility to make changes.
 *
 * DIP - Dependency Inversion Principle
 * High-level modules should not depend on low-level modules. Both should depend on the abstraction.
 * Abstractions should not depend on details. Details should depend on abstractions.
 * **/

interface MovieRepository {

    fun getMovies(apiKey: String, language: String): Single<List<Movie>>

    fun getMovieDetail(movieId: String, apiKey: String, language: String, appendToResponse: String): Single<MovieDetail>

    fun getGenres(apiKey: String, language: String): Single<List<Genre>>

    fun getMoviesByGenre(apiKey: String, language: String, genres: String): Single<List<Movie>>

    fun searchMovie(apiKey: String, language: String, query: String): Single<List<Movie>>

}