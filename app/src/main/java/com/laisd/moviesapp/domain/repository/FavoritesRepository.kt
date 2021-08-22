package com.laisd.moviesapp.domain.repository

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

interface FavoritesRepository {

    fun addFavoriteMovie(movie: Movie, movieDetail: MovieDetail)

    fun deleteFavoriteMovie(movie: Movie, movieDetail: MovieDetail)

    fun getFavoriteMovies(): Single<List<Movie>>

    fun getFavoriteMovieDetail(movieId: Int): Single<MovieDetail>
}