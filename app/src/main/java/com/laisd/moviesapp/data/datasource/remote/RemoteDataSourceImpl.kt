package com.laisd.moviesapp.data.datasource.remote

import com.laisd.moviesapp.data.api.MovieDetailService
import com.laisd.moviesapp.data.api.MovieGenresService
import com.laisd.moviesapp.data.api.MoviesService
import com.laisd.moviesapp.data.model.GenreListResponse
import com.laisd.moviesapp.data.model.MovieDetailResponse
import com.laisd.moviesapp.data.model.MoviesListResponse
import io.reactivex.rxjava3.core.Single

class RemoteDataSourceImpl(
    private val moviesService: MoviesService,
    private val movieDetailService: MovieDetailService,
    private val movieGenresService: MovieGenresService
) : RemoteDataSource {

    override fun getMovies(apiKey: String, language: String): Single<MoviesListResponse> =
        moviesService.getPopularMoviesResponse(apiKey, language)

    override fun getMovieDetail(
        movieId: Int?,
        apiKey: String,
        language: String,
        appendToResponse: String
    ): Single<MovieDetailResponse> =
        movieDetailService.getMovieDetailResponse(
            movieId.toString(),
            apiKey,
            language,
            appendToResponse
        )

    override fun getGenres(apiKey: String, language: String): Single<GenreListResponse> =
        movieGenresService.getGenresResponse(apiKey, language)

}