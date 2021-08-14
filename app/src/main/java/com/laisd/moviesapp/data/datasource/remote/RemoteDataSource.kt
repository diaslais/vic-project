package com.laisd.moviesapp.data.datasource.remote

import android.net.Uri
import com.laisd.moviesapp.data.model.remote.GenreListResponse
import com.laisd.moviesapp.data.model.remote.MovieDetailResponse
import com.laisd.moviesapp.data.model.remote.MoviesListResponse
import io.reactivex.rxjava3.core.Single

interface RemoteDataSource {

    fun getMovies(apiKey: String, language: String): Single<MoviesListResponse>

    fun getMovieDetail(movieId: Int?, apiKey: String, language: String, appendToResponse: String): Single<MovieDetailResponse>

    fun getGenres(apiKey: String, language: String): Single<GenreListResponse>

    fun getMoviesByGenre(apiKey: String, language: String, genres: String): Single<MoviesListResponse>

    fun searchMovie(apiKey: String, language: String, query: Uri): Single<MoviesListResponse>

}