package com.laisd.moviesapp.data.datasource.remote

import android.net.Uri
import com.laisd.moviesapp.data.api.*
import com.laisd.moviesapp.data.model.remote.GenreListResponse
import com.laisd.moviesapp.data.model.remote.MovieDetailResponse
import com.laisd.moviesapp.data.model.remote.MoviesListResponse
import io.reactivex.rxjava3.core.Single

class RemoteDataSourceImpl(
    private val moviesService: MoviesService,
    private val movieDetailService: MovieDetailService,
    private val genresService: GenresService,
    private val moviesByGenreService: MoviesByGenreService,
    private val searchMovieService: SearchMovieService
) : RemoteDataSource {

    override fun getMovies(apiKey: String, language: String): Single<MoviesListResponse> =
        moviesService.getPopularMoviesResponse(apiKey, language)

    override fun getMovieDetail(
        movieId: String,
        apiKey: String,
        language: String,
        appendToResponse: String
    ): Single<MovieDetailResponse> =
        movieDetailService.getMovieDetailResponse(
            movieId,
            apiKey,
            language,
            appendToResponse
        )

    override fun getGenres(apiKey: String, language: String): Single<GenreListResponse> =
        genresService.getGenresResponse(apiKey, language)

    override fun getMoviesByGenre(
        apiKey: String,
        language: String,
        genres: String
    ): Single<MoviesListResponse> =
        moviesByGenreService.getMoviesByGenre(apiKey, language, genres)

    override fun searchMovie(
        apiKey: String,
        language: String,
        query: Uri
    ): Single<MoviesListResponse> =
        searchMovieService.searchMovie(apiKey, language, query)
}