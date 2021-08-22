package com.laisd.moviesapp.data.datasource.local

import com.laisd.moviesapp.data.model.local.MovieEntity
import io.reactivex.rxjava3.core.Single

/**
 * Reduce dependency between classes through an interface (abstraction),
 * which brings more flexibility to make changes.
 *
 * DIP - Dependency Inversion Principle
 * High-level modules should not depend on low-level modules. Both should depend on the abstraction.
 * Abstractions should not depend on details. Details should depend on abstractions.
 * **/

interface LocalDataSource {

    fun addFavoriteMovie(movieEntity: MovieEntity)

    fun deleteFavoriteMovie(movieEntity: MovieEntity)

    fun getFavoriteMovies(): Single<List<MovieEntity>>

    fun getFavoriteMovieDetail(movieId: Int): Single<MovieEntity>
}