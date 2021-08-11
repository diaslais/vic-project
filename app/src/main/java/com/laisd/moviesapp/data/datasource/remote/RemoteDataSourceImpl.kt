package com.laisd.moviesapp.data.datasource.remote

import com.laisd.moviesapp.data.api.*
import com.laisd.moviesapp.data.model.GenreListResponse
import com.laisd.moviesapp.data.model.MovieDetailResponse
import com.laisd.moviesapp.data.model.MoviesListResponse
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
        query: String
    ): Single<MoviesListResponse> =
        searchMovieService.searchMovie(apiKey, language, query)
}