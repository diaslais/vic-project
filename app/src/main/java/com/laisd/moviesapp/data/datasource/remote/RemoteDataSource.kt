package com.laisd.moviesapp.data.datasource.remote

import android.net.Uri
import com.laisd.moviesapp.data.model.remote.GenreListResponse
import com.laisd.moviesapp.data.model.remote.MovieDetailResponse
import com.laisd.moviesapp.data.model.remote.MoviesListResponse
import io.reactivex.rxjava3.core.Single

/**
 * Reduce dependency between classes through an interface (abstraction),
 * which brings more flexibility to make changes.
 *
 * DIP - Dependency Inversion Principle
 * High-level modules should not depend on low-level modules. Both should depend on the abstraction.
 * Abstractions should not depend on details. Details should depend on abstractions.
 * **/

interface RemoteDataSource {

    fun getMovies(apiKey: String, language: String): Single<MoviesListResponse>

    fun getMovieDetail(movieId: String, apiKey: String, language: String, appendToResponse: String): Single<MovieDetailResponse>

    fun getGenres(apiKey: String, language: String): Single<GenreListResponse>

    fun getMoviesByGenre(apiKey: String, language: String, genres: String): Single<MoviesListResponse>

    fun searchMovie(apiKey: String, language: String, query: Uri): Single<MoviesListResponse>

}