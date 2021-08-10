package com.laisd.moviesapp.data.datasource.remote

import com.laisd.moviesapp.data.model.GenreListResponse
import com.laisd.moviesapp.data.model.MovieDetailResponse
import com.laisd.moviesapp.data.model.MoviesListResponse
import io.reactivex.rxjava3.core.Single

interface RemoteDataSource {

    fun getMovies(apiKey: String, language: String): Single<MoviesListResponse>

    fun getMovieDetail(movieId: Int?, apiKey: String, language: String, appendToResponse: String): Single<MovieDetailResponse>

    fun getGenres(apiKey: String, language: String): Single<GenreListResponse>

}